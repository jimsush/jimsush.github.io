package org.sf.ora2h.mockdata;

import org.sf.ora2h.model.MetaData;
import org.sf.ora2h.model.Payload;
import org.sf.ora2h.model.VarMetaData;

public class MockData {

	public static MetaData genMetaData(long dspi) {
		return new MetaData(dspi, dspi+"MMM");
	}
	
	public static VarMetaData[] genVarMetaData(long dspi){
		//null,dsp_v1, dspi_v1_v2,dspi_v1_v2_v3
		int mod=(int)dspi%4;
		switch(mod){
		case 0:
			return null;
		case 1:
			VarMetaData v1=new VarMetaData(dspi, dspi+"V1");
			return new VarMetaData[]{v1};
		case 2:
			VarMetaData v11=new VarMetaData(dspi, dspi+"V1");
			VarMetaData v22=new VarMetaData(dspi, dspi+"V2");
			return new VarMetaData[]{v11,v22};
		case 3:
			VarMetaData v111=new VarMetaData(dspi, dspi+"V1");
			VarMetaData v222=new VarMetaData(dspi, dspi+"V2");
			VarMetaData v333=new VarMetaData(dspi, dspi+"V3");
			return new VarMetaData[]{v111,v222,v333};
		default:
			return null;
		}
	}
	
	public static Payload[] genPayload(long dspi){
		//null,dsp_v1, dspi_v1_v2,dspi_v1_v2_v3
		int mod=(int)dspi%5;
		switch(mod){
		case 0:
			return null;
		case 1:
			Payload p1=new Payload(dspi, dspi+"P1");
			return new Payload[]{p1};
		case 2:
			Payload p11=new Payload(dspi, dspi+"P1");
			Payload p22=new Payload(dspi, dspi+"P2");
			return new Payload[]{p11, p22};
		case 3:
			Payload p111=new Payload(dspi, dspi+"P1");
			Payload p222=new Payload(dspi, dspi+"P2");
			Payload p333=new Payload(dspi, dspi+"P3");
			return new Payload[]{p111,p222,p333};
		case 4:
			return null;
		default:
			return null;
		}
	}
	
}
