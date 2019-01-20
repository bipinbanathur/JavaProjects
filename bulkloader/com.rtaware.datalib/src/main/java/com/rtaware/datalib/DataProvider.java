package com.rtaware.datalib;
import com.rtaware.datalib.config.EntityType;
public interface DataProvider extends AutoCloseable
{
	void 			setEntityType	(EntityType entityType);
	String			getValue		(String entitySpecifier, Integer rowIndex, Integer columnIndex);
	public void close();
}
