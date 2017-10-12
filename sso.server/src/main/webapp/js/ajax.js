
/**
 * 同步的ajax方法
 * type 1表示纯文本，2表示json, 3表示xml
 */
function ajax(method, url, params, type){
	
	if(!params){
		alert("参数应该是一个js对象");
		return;
	};
	
	var xmlhttp;
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	var result;
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState==4 && xmlhttp.status==200){
			if(type ==1 )
			{
				result = xmlhttp.responseText;
			}else if(type == 2)
			{
				result = xmlhttp.responseText;
			}else if(type == 3)
			{
				result = xmlhttp.responseXML;
			}else{
				result = "未知数据类型";
			}
		}
	};
	
	var paramString = "";
	for(var i in params){
		paramString += (i+"="+params[i])+"&";
	}
	
	paramString = paramString.substring(0, paramString.lastIndexOf("&"));
	
	if("GET" == method || "get" == method)
	{
		if(paramString){
			xmlhttp.open("GET", url+"?"+paramString, false);
		}else{
			xmlhttp.open("GET", url, false);
		}
		
		xmlhttp.send();
		
	}else if("POST" == method || "post" == method)
	{
		xmlhttp.open("POST", url, false);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send(paramString);
	}
	
	return result;
}