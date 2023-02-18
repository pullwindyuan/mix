/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.universe.wallet.encryptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.util.EncodingUtils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Encryptor that uses AES encryption.
 *
 * @author Keith Donald
 * @author Dave Syer
 */
@Slf4j
@Data
public final class ExtendAesBytesEncryptor implements BytesEncryptor {
	private final SecretKey secretKey;
	private final Cipher encryptor;
	private final Cipher decryptor;
	private final BytesKeyGenerator ivGenerator;
	private CipherAlgorithm alg;
	private static final String AES_CBC_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";

	private static final int NONCE_KEY_LENGTH = 12;
	private static final String DEFAULT_SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final BytesKeyGenerator DEFAULT_BYTES_KEY_GENERATOR = KeyGenerators.secureRandom(NONCE_KEY_LENGTH);
	private static BytesKeyGenerator bytesKeyGenerator = null;


	/**
	 * Constructs an encryptor that uses AES encryption.
	 * But the key is not security key
	 * @param key the secret (symmetric) key bytes
	 * @param ivGenerator the generator used to generate the initialization vector. If
	 * null, then a default algorithm will be used based on the provided
	 * {@link CipherAlgorithm}
	 * @param alg the {@link CipherAlgorithm} to be used
	 */
	public ExtendAesBytesEncryptor(byte[] key, BytesKeyGenerator ivGenerator, CipherAlgorithm alg) {
		this.secretKey = new SecretKeySpec(key,  "AES");
		this.alg = alg;
		this.encryptor = alg.createCipher();
		this.decryptor = alg.createCipher();
		this.ivGenerator = (ivGenerator != null) ? ivGenerator : alg.defaultIvGenerator();
	}

	@Override
	public byte[] encrypt(byte[] bytes) {
		synchronized (this.encryptor) {
			byte[] iv = this.ivGenerator.generateKey();
			initCipher(this.encryptor, Cipher.ENCRYPT_MODE, this.secretKey, this.alg.getParameterSpec(iv));
			byte[] encrypted = doFinal(this.encryptor, bytes);
			return (this.ivGenerator != NULL_IV_GENERATOR) ? EncodingUtils.concatenate(iv, encrypted) : encrypted;
		}
	}

	@Override
	public byte[] decrypt(byte[] encryptedBytes) {
		synchronized (this.decryptor) {
			byte[] iv = iv(encryptedBytes);
			initCipher(this.decryptor, Cipher.DECRYPT_MODE, this.secretKey, this.alg.getParameterSpec(iv));
			return doFinal(this.decryptor,
					(this.ivGenerator != NULL_IV_GENERATOR) ? encrypted(encryptedBytes, iv.length) : encryptedBytes);
		}
	}

	private byte[] iv(byte[] encrypted) {
		return (this.ivGenerator != NULL_IV_GENERATOR)
				? EncodingUtils.subArray(encrypted, 0, this.ivGenerator.getKeyLength())
				: NULL_IV_GENERATOR.generateKey();
	}

	private byte[] encrypted(byte[] encryptedBytes, int ivLength) {
		return EncodingUtils.subArray(encryptedBytes, ivLength, encryptedBytes.length);
	}

	private static final BytesKeyGenerator NULL_IV_GENERATOR = new BytesKeyGenerator() {

		private final byte[] value = new byte[16];

		@Override
		public int getKeyLength() {
			return this.value.length;
		}

		@Override
		public byte[] generateKey() {
			return this.value;
		}

	};

	public enum CipherAlgorithm {
		/**|
		 * CBC 模式
		 */
		CBC(AES_CBC_ALGORITHM, NULL_IV_GENERATOR),

		/**
		 * GCM 模式
		 */
		GCM(AES_GCM_ALGORITHM, DEFAULT_BYTES_KEY_GENERATOR);

		private final BytesKeyGenerator ivGenerator;

		private final String name;

		CipherAlgorithm(String name, BytesKeyGenerator ivGenerator) {
			this.name = name;
			this.ivGenerator = ivGenerator;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public AlgorithmParameterSpec getParameterSpec(byte[] iv) {
			return (this != CBC) ? new GCMParameterSpec(128, iv) : new IvParameterSpec(iv);
		}

		public Cipher createCipher() {
			return newCipher(this.toString());
		}

		public BytesKeyGenerator defaultIvGenerator() {
			return this.ivGenerator;
		}

	}

	/**
	 * Creates a standard password-based bytes encryptor using 256 bit AES encryption with
	 * Galois Counter Mode (GCM). Derives the secret key using PKCS #5's PBKDF2
	 * (Password-Based Key Derivation Function #2). But without salts the password to prevent
	 * dictionary attacks against the key. So, it's not a security key. Requires Java 6.
	 * @param password the password used to generate the encryptor's secret key; should
	 * not be shared
	 * key
	 */
	public static BytesEncryptor weakKeyStronger(String password) {
		return new ExtendAesBytesEncryptor(password.getBytes(StandardCharsets.UTF_8), null, CipherAlgorithm.GCM);
	}

	/**
	 * Constructs a new Cipher.
	 */
	public static Cipher newCipher(String algorithm) {
		try {
			return Cipher.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalArgumentException("Not a valid encryption algorithm", ex);
		}
		catch (NoSuchPaddingException ex) {
			throw new IllegalStateException("Should not happen", ex);
		}
	}

	/**
	 * Initializes the Cipher for use.
	 */
	public static void initCipher(Cipher cipher, int mode, SecretKey secretKey, AlgorithmParameterSpec parameterSpec) {
		try {
			if (parameterSpec != null) {
				cipher.init(mode, secretKey, parameterSpec);
			}
			else {
				cipher.init(mode, secretKey);
			}
		}
		catch (InvalidKeyException ex) {
			throw new IllegalArgumentException("Unable to initialize due to invalid secret key", ex);
		}
		catch (InvalidAlgorithmParameterException ex) {
			throw new IllegalStateException("Unable to initialize due to invalid decryption parameter spec", ex);
		}
	}

	/**
	 * Invokes the Cipher to perform encryption or decryption (depending on the
	 * initialized mode).
	 */
	public static byte[] doFinal(Cipher cipher, byte[] input) {
		try {
			return cipher.doFinal(input);
		}
		catch (IllegalBlockSizeException ex) {
			throw new IllegalStateException("Unable to invoke Cipher due to illegal block size", ex);
		}
		catch (BadPaddingException ex) {
			throw new IllegalStateException("Unable to invoke Cipher due to bad padding", ex);
		}
	}

}
