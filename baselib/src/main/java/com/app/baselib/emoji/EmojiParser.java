package com.app.baselib.emoji;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 11/10/18
 * @desc ...
 */
public class EmojiParser {
	protected Context mContext;
	protected Map<String,Integer> mEmojiMap;
	protected Pattern mPattern;
	protected String[] mEmojiTexts;
	
	private EmojiParser(Builder builder) {
		mContext = builder.mContext;
		mEmojiMap = builder.mEmojiMap;
		if (mEmojiMap == null) {
			throw new NullPointerException("please set emoji text and resouceId");
		}
		mEmojiTexts = new String[mEmojiMap.size()];
		mEmojiTexts = mEmojiMap.keySet().toArray(mEmojiTexts);
		mPattern = buildPattern();
	}
	
	public static Builder with(Context context) {
		return new Builder(context);
	}
	
	// 构建正则表达式
	protected Pattern buildPattern() {
		StringBuilder patternString = new StringBuilder(mEmojiTexts.length * 3);
		
		patternString.append('(');
		for (String s : mEmojiTexts) {
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		patternString.replace(patternString.length() - 1,patternString.length(),")");
		
		return Pattern.compile(patternString.toString());
	}
	
	// 根据文本替换成图片
	protected CharSequence replace(CharSequence text) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		
		Matcher matcher = mPattern.matcher(text);
		while (matcher.find()) {
			int resId = mEmojiMap.get(matcher.group());
			builder.setSpan(new ImageSpan(mContext,resId),matcher.start(),matcher.end(),
			                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		return builder;
	}
	
	
	public static final class Builder {
		private Context mContext;
		private Map<String,Integer> mEmojiMap;
		
		private Builder(Context val) {
			mContext = val;
		}
		
		public Builder emoji(Map<String,Integer> val) {
			mEmojiMap = val;
			return this;
		}
		
		public Builder addEmoji(String emojiText,@DrawableRes int emojiRes) {
			if (mEmojiMap==null){
				mEmojiMap = new HashMap<>();
			}
			mEmojiMap.put(emojiText,emojiRes);
			return this;
		}
		
		public EmojiParser build() {
			return new EmojiParser(this);
		}
	}
}
