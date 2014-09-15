package com.ocyd.jeecgframework.core.groovy.impl;

import com.ocyd.jeecgframework.core.groovy.IScript;
import org.springframework.stereotype.Component;

/**
 * 系统脚本
 */
@Component
public class ScriptImpl implements IScript {
	public String getCurrentUserId() {
		return "1";
	}
}