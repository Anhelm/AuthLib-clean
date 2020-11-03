package com.mojang.authlib.minecraft;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class InsecureTextureException extends RuntimeException
{

	public InsecureTextureException(String message) {
		super(message);
	}
	public static class MissingTextureException extends InsecureTextureException
	{

		public MissingTextureException() {
			super("No texture information found");
		}
	}
	
	public static class OutdatedTextureException  extends InsecureTextureException
	{

		private final Date validFrom;
		private final Calendar limit;
		
		public OutdatedTextureException(Date validFrom, Calendar limit)
		{
			super("Decrypted textures payload is too old (" + validFrom + ", but we need to be at least " + limit + ")");
			this.validFrom = validFrom;
			this.limit = limit;
		}
		
		protected Date getValidFrom() {
			return this.validFrom;
		}
		protected Calendar getLimit() {
			return this.limit;
		}
	}
	
	public static class WrongTextureOwnerException extends InsecureTextureException
	{

		private final GameProfile expected;
		private final UUID resultId;
		private final String resultName;
		
		public WrongTextureOwnerException(GameProfile expected, UUID resultId, String resultName)
		{
			super("Decrypted textures payload was for another user (expected " + expected.getId() + "/" + expected.getName() + " but was for " + resultId + "/" + resultName + ")");
			this.expected = expected;
			this.resultId = resultId;
			this.resultName = resultName;
		}
		
		protected GameProfile getGameProfile() {
			return this.expected;
		}
		protected UUID getUUID() {
			return resultId;
		}
		protected String getResultName() {
			return resultName;
		}
	}
}