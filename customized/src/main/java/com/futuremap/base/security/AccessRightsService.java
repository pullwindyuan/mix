package com.futuremap.base.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 使用非对称加密的JWT的方式来验证客户端是否有访问某个API或者资源的权限
 * 控制流程：
 * 1、先预生成一对秘钥，然后使用公钥对用户的授权访问资源ID加密生成一个JWT；
 * 2、将JWT分发给客户端；
 * 3、客户端每次请求数据均携带这个JWT;
 * 4、服务端收到请求后提取JWT并用私钥解密；
 * 5、解密成功后对比受访资源ID是否在授权名单当中，如果在表示可以访问，不在
 *       就拒绝访问。
 * @author Pullwind
 */
@Service
@Slf4j
public class AccessRightsService {

}
