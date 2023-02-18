var popup_dialog, popup_dialog_background, button1_handle, button2_handle, button1_text, button2_text, title_text;

String.prototype.getWidth = function(fontSize)
{
    span = document.createElement("div");
    span.id = "__getwidth";
    document.body.appendChild(span);
    span.style.visibility = "hidden";
    span.style.whiteSpace = "nowrap";
    var temp = span.offsetWidth;
    span.innerText = this;
    span.style.fontSize = fontSize + "px";
    temp = span.offsetWidth - temp;
    span.parentNode.removeChild(span);
    return temp;
}

function createPopup(container) {
    createAndSetPopup(container, null, null, null, null, null);
}

function createAndSetPopup(container, title, button1_name, button2_name, button1_callback, button2_callback) {
    var html;
    button1_handle = button1_callback;
    button2_handle = button2_callback;
    button1_text = button1_name;
    button2_text = button2_name;
    title_text = title;
    //添加背景
    popup_dialog_background = document.createElement("div");
    popup_dialog_background.setAttribute("class", "pop_up_background");
    popup_dialog_background.setAttribute("id", "pop_up_background");
    container.appendChild(popup_dialog_background);
    //插入样式
    popup_dialog_background.innerHTML = "<link rel='stylesheet' href='./css/popup.css'>";

    //添加总容器
    popup_dialog = document.createElement("div");
    popup_dialog.setAttribute("id", "pop_up_dialog");
    popup_dialog.style.position = "absolute";
    popup_dialog.style.backgroundColor = "transparent";
    popup_dialog.style.left = "0px";
    popup_dialog.style.top = "0px";
    popup_dialog.style.width = "100%";
    popup_dialog.style.height = "100%";
    popup_dialog.style.opacity = "1.0";
    container.appendChild(popup_dialog);
//	    //插入样式
//	    popup_dialog.innerHTML = "<link rel='stylesheet' href='Popup.css'>";
//        //添加背景
//        html = "<div class='pop_up_background' id='pop_up_background'></div>";
//        popup_dialog.innerHTML += html;

    //添加弹框布局
    html = "<table  width=" + document.body.clientWidth + " height=" + document.body.clientHeight + " bordser='1'>" +
                "<tr id='blank_top' height=" + document.body.clientHeight*0.3 + "></tr>" +
//                    "<tr height=" + document.body.clientHeight/25 + ">" +
//                        "<td width='10%' style='background-color:transparent;'></td>" +
//                        "<td class='pop_title' width='80%' >" + title + "</td>" +
//                        "<td width='10%' style='background-color:transparent;'></td>" +
//                    "</tr>" +
                "<tr id='contents' height=" + document.body.clientHeight*0.4 + " valign='top'>" +
                    "<td width='10%' style='background-color:transparent;'></td>" +
                    "<td width='80%' id='pop_content' style='background-color:white;'>" +

                    "</td>" +
                    "<td width='10%' style='background-color:transparent;'></td>" +
                "</tr>" +
//                    "<tr align='center' height=" + document.body.clientHeight/20 + ">" +
//                        "<td width=" + document.body.clientWidth/10 + " style='background-color:transparent;'></td>" +
//                        "<td align='center' width=" + document.body.clientWidth*0.8 + " height=" + document.body.clientHeight/20 + " style='background-color:white;text-align:center;'>" +
//                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='background-color:transparent;'></div>" +
//                            "<div class='pop_button_ok' style='width:35%;height:" + document.body.clientHeight/25 + "px;line-height:" + document.body.clientHeight/25 + "px;text-align:center;'>" + button1_name + "</div>" +
//                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='background-color:transparent;'></div>" +
//                            "<div class='pop_button_back' style='width:35%;height:" + document.body.clientHeight/25 + "px;line-height:" + document.body.clientHeight/25 + "px;text-align:center;'>" + button2_name + "</div>" +
//                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='background-color:transparent;'></div>" +
//                        "</td>" +
//                        "<td width=" + document.body.clientWidth/10 + " style='background-color:transparent;'></td>" +
//                    "</tr>" +
                "<tr id='blank_bottom' height=" + document.body.clientHeight*0.3 + "></tr>" +
            "</table></div>";
    popup_dialog.innerHTML += html;
    //popup_dialog.addEventListener( Touch.Start, popupClickDown, true);
    popup_dialog.onclick = popupClickUp;
    popup_dialog.style.display = "none";
    popup_dialog_background.style.display = "none";
}

function setAndShowPopup(content, title, button1_name, button2_name, button1_callback, button2_callback) {
    var pop_content = document.getElementById("pop_content");
    if(pop_content == null) {
        createPopup(document.body);
        pop_content = document.getElementById("pop_content");
    }
    var pop_height = parseFloat(document.getElementById("contents").getAttribute("height"));
    var pop_title_height = 25;
    var pop_button_bar_height = 35;
    var button1, button2;
    var content_height = pop_height - pop_title_height - pop_button_bar_height - 10;

    title_text = title;
    button1_handle = button1_callback;
    button2_handle = button2_callback;
    button1_text = button1_name;
    button2_text = button2_name;
    popup_dialog.style.opacity = "1.0";
    if(button1_name == null && button2_name == null) {
        pop_button_bar_height = 0;
        content_height = pop_height;
    }
    if(button1_name != null && button2_name != null) {
        button1 = "<div class='pop_button_ok' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button1_text + "</div>";
        button2 = "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                "<div class='pop_button_back' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button2_text + "</div>";
    }else {
        if(button1_name != null) {
            if(button2_name == null) {
                button1 = "<div class='pop_button_ok' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:80%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button1_text + "</div>";
            }else {
                button1 = "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                "<div class='pop_button_ok' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button1_text + "</div>";
            }
        }else {
            button1 = "";
        }
        if(button2_name != null) {
            if(button1_name == null) {
                button2 = "<div class='pop_button_back' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:80%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button2_text + "</div>";
            }else {
                button2 = "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                "<div class='pop_button_back' style='background-color:#b2f3fb;margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button2_text + "</div>";
            }
        }else {
            button2 = "";
        }
    }

    pop_content.innerHTML = "<div class='pop_title' style='height:" + pop_title_height + "px;line-height:" + pop_title_height + "px;'>" + title_text + "</div>" +
                            "<div class='customer' id='customer_contents' style='height:" + content_height + "px;'>" + content + "</div>" +
                            "<div class='pop_blank' style='height:" + 20 + "px;'></div>" +
                            "<div class='button_bar' style='height:" + pop_button_bar_height + "px'>" +
                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                            button1 +
                            button2 +
                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                            "</div>";
    popup_dialog_background.style.display = "";
    popup_dialog.style.display = "";
}

function showAlert(title, msg, time_out) {
    var html = "<div class='pop_content_text' id='pop_content_text' style='text-align:center;height:100%;font-size:16px;border:10px;padding:10px;word-break:break-all;'>" +
                msg +
                "</div>";
    var string_width = msg.getWidth(16);
    var contents_height = 16 * (string_width/(document.body.clientWidth * 0.8)) + 10;
    var blank_height = (document.body.clientHeight - contents_height)/2;

    setAndShowPopup(html, title, null, null, null, null);
    var blank_top = document.getElementById("blank_top");
    var blank_bottom = document.getElementById("blank_bottom");
    var contents = document.getElementById("contents");
    var customer_contents = document.getElementById("customer_contents");
    blank_top.setAttribute("height", blank_height + "px");
    contents.setAttribute("height", contents_height + "px");
    customer_contents.style.height = contents_height + "px";
    blank_bottom.setAttribute("height", blank_height + "px");

    setTimeout("fadingPopup(100)", 1000);
}

function hiddenPopup() {
    popup_dialog.style.display = "none";
    popup_dialog_background.style.display = "none";
}

function fadingPopup(duration) {
    var at = setInterval(function fadingPopup() {
                        var opacity;
                        opacity = parseFloat(popup_dialog.style.opacity);
                        if(opacity > 0) {
                            opacity -= 0.05;
                            if(opacity <= 0) {
                                clearInterval(at);
                                hiddenPopup();
                            }else {
                                popup_dialog.style.opacity = "" + opacity;
                                //clearTimeout(setTimeout(fadingPopup(time_out), time_out));
                                //setTimeout(fadingPopup(time_out), time_out);
                            }
                        }else {
                             clearInterval();
                             hiddenPopup();
                        }
                    }, duration);
}

function showPopup(content) {
    var pop_content = document.getElementById("pop_content");
    var pop_height = document.body.clientHeight*0.4;
    var pop_title_height = 60;
    var pop_button_bar_height = 100;

    button1_text = "我知道了";
    button2_text = "关闭";
    pop_content.innerHTML = "<div class='pop_title' style='height:" + pop_title_height + "px;line-height:" + pop_title_height + "px;'>" + title_text + "</div>" +
                            "<div class='customer' style='height:" + (pop_height - pop_title_height - pop_button_bar_height - 20) + "px'>" + content + "</div>" +
                            "<div class='pop_blank' style='height:" + 20 + "px;'></div>" +
                            "<div class='button_bar' style='height:" + pop_button_bar_height + "px'>" +
                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                            "<div class='pop_button_ok' style='margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button1_text + "</div>" +
                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px height='20%' style='display:inline;background-color:transparent;'></div>" +
                            "<div class='pop_button_back' style='margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button2_text + "</div>" +
                            "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
                            "</div>";
    popup_dialog_background.style.display = "";
    popup_dialog.style.display = "";
}

function showPopup(content, button1_callback, button2_callback, button1_name, button2_name) {
    var pop_content = document.getElementById("pop_content");
    var pop_height = document.body.clientHeight*0.4;
    var pop_title_height = 60;
    var pop_button_bar_height = 100;
    button1_handle = button1_callback;
    button2_handle = button2_callback;
    button1_text = button1_name;
    button2_text = button2_name;

    pop_content.innerHTML = "<div class='pop_title' style='height:" + pop_title_height + "px;line-height:" + pop_title_height + "px;'>" + title_text + "</div>" +
        "<div class='customer' style='height:" + (pop_height - pop_title_height - pop_button_bar_height - 20) + "px'>" + content + "</div>" +
        "<div class='pop_blank' style='height:" + 20 + "px;'></div>" +
        "<div class='button_bar' style='height:" + pop_button_bar_height + "px'>" +
        "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
        "<div class='pop_button_ok' style='margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button1_text + "</div>" +
        "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px height='20%' style='display:inline;background-color:transparent;'></div>" +
        "<div class='pop_button_back' style='margin:" + pop_button_bar_height*0.1 + "px 0px 0px 0px;display:inline;width:35%;height:" + pop_button_bar_height*0.8 + "px;line-height:" + pop_button_bar_height*0.8 + "px;text-align:center;'>" + button2_text + "</div>" +
        "<div class='blank_10' width=" + document.body.clientWidth*0.8*0.1 + "px style='display:inline;background-color:transparent;'></div>" +
        "</div>";
    popup_dialog_background.style.display = "";
    popup_dialog.style.display = "";
}

function popupClickUp(e) {
    if(e.target.className == "pop_button_ok") {
//            var curr_block = document.getElementById(curr_color_block_id);
//            curr_block.style.backgroundColor = curr_177_color_hex;
//            curr_block.parentNode.children[2].innerHTML = curr_177_color_rgb;
//            curr_block.parentNode.children[3].innerHTML = curr_177_color_hex;
//            popup_dialog.style.display = "none";
        if(button1_handle != null) {
            button1_handle();
        }
    }else if(e.target.className == "pop_button_back") {
        if(button2_handle != null) {
            button2_handle();
        }
    }
    //hiddenPopup();
    fadingPopup(30);
}

function popupClickDown(e) {
    if(e.target.className == "pop_button_ok") {
//            var curr_block = document.getElementById(curr_color_block_id);
//            curr_block.style.backgroundColor = curr_177_color_hex;
//            curr_block.parentNode.children[2].innerHTML = curr_177_color_rgb;
//            curr_block.parentNode.children[3].innerHTML = curr_177_color_hex;
//            popup_dialog.style.display = "none";
        button1_handle.style.backgroundColor = "orange";
    }else if(e.target.className == "pop_button_back") {
        button2_handle.style.backgroundColor = "orange";
    }
}