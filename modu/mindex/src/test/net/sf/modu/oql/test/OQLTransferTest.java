package net.sf.modu.oql.test;

import junit.framework.Assert;
import net.sf.modu.oql.OQLTransfer;
import net.sf.modu.oql.OQLTransferFactory;

import org.junit.Test;


public class OQLTransferTest {

	@Test
	public void getHintExpression(){
		OQLTransfer hint=OQLTransferFactory.createOQLTransfer("addhint(/* IDX_TABLE$1 */)");
		String hintExp=hint.tranfer("select * from table1");
		Assert.assertEquals("/* IDX_TABLE$1 */", hintExp);
	}

	@Test
	public void getReplaceExpression(){
		OQLTransfer transfer=OQLTransferFactory.createOQLTransfer(" and IsOffice->L and IsOffice");
		String newOQL=transfer.tranfer("select * from table1 where TradeId=12345 and IsOffice=Y and Field=abc");
		Assert.assertEquals("select * from table1 where TradeId=12345L and IsOffice=Y and Field=abc", newOQL);
	}
	
	@Test
	public void getRegexReplaceExpression(){
		OQLTransfer transfer=OQLTransferFactory.createOQLTransfer("(TradeId=[1-9][0-9]{1,}) and IsOffice->$1L and IsOffice");
		String newOQL=transfer.tranfer("select * from table1 where TradeId=123456 and IsOffice=Y and Field=abc");
		Assert.assertEquals("select * from table1 where TradeId=123456L and IsOffice=Y and Field=abc", newOQL);
	}
	
}
