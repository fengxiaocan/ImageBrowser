package com.app.baselib.emoji;

import android.widget.TextView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/10/18
 * @desc 一个emoji帮助工具类
 */
public class EmojiHelper {
	private static EmojiParser mEmojiParser;
	
	public static void initEmoji(EmojiParser parser) {
		mEmojiParser = parser;
	}
	
	public static CharSequence replace(CharSequence text) {
		if (mEmojiParser == null) {
			throw new NullPointerException("Please init EmojiHelper");
		}
		return mEmojiParser.replace(text);
	}
	
	public static void parser(TextView view,CharSequence text) {
		view.setText(replace(text));
	}
}
