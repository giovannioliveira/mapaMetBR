package ufrj.museunacional.infogeo.util;

import ufrj.museunacional.infogeo.R;

public class GeneralUtil {

	public static String massConversion(Double mass) {
		if (mass < 1) {
			mass *= 1000;
			return String.format("%.2fg", mass);
		} else if (mass < 1000) {
			return String.format("%.2fKg", mass);
		} else {
			mass /= 1000;
			return String.format("%.2fton.", mass);
		}
	}
	
	public static String formatDate(String date){
		return "temp";
	}

	public static int[] getImageResources(int id) {

		int result[] = { 0, 0 };

		switch (id) {
		case 1:
			result[0] = R.drawable.image3;
			result[1] = R.drawable.image2;
			break;
		case 2:
			result[0] = R.drawable.image5;
			result[1] = R.drawable.image4;
			break;
		case 3:
			result[0] = R.drawable.image6;
			result[1] = 0;
			break;
		case 4:
			result[0] = R.drawable.image8;
			result[1] = R.drawable.image9;
			break;
		case 5:
			result[0] = R.drawable.image11;
			result[1] = 0;
			break;
		case 6:
			result[0] = R.drawable.image13;
			result[1] = 0;
			break;
		case 7:
			result[0] = R.drawable.image15;
			result[1] = R.drawable.image14;
			break;
		case 8:
			result[0] = R.drawable.image16;
			result[1] = R.drawable.image17;
			break;
		case 9:
			result[0] = R.drawable.image21;
			result[1] = R.drawable.image77;
			break;
		case 10:
			result[0] = R.drawable.image23;
			result[1] = R.drawable.image22;
			break;
		case 11:
			result[0] = R.drawable.image24;
			result[1] = R.drawable.image25;
			break;
		case 12:
			result[0] = R.drawable.image27;
			result[1] = R.drawable.image26;
			break;
		case 13:
			result[0] = R.drawable.image29;
			result[1] = R.drawable.image30;
			break;
		case 14:
			result[0] = R.drawable.image33;
			result[1] = 0;
			break;
		case 15:
			result[0] = R.drawable.image34;
			result[1] = R.drawable.image35;
			break;
		case 16:
			result[0] = 0;
			result[1] = 0;
			break;
		case 17:
			result[0] = R.drawable.image36;
			result[1] = R.drawable.image37;
			break;
		case 18:
			result[0] = R.drawable.image38;
			result[1] = R.drawable.image39;
			break;
		case 19:
			result[0] = R.drawable.image40;
			result[1] = 0;
			break;
		case 20:
			result[0] = R.drawable.image41;
			result[1] = R.drawable.image42;
			break;
		case 21:
			result[0] = R.drawable.image43;
			result[1] = R.drawable.image44;
			break;
		case 22:
			result[0] = R.drawable.image45;
			result[1] = R.drawable.image46;
			break;
		case 23:
			result[0] = R.drawable.image49;
			result[1] = R.drawable.image50;
			break;
		case 24:
			result[0] = R.drawable.image51;
			result[1] = R.drawable.image52;
			break;
		case 25:
			result[0] = R.drawable.image53;
			result[1] = R.drawable.image54;
			break;
		case 26:
			result[0] = R.drawable.image59;
			result[1] = 0;
			break;
		case 27:
			result[0] = R.drawable.image55;
			result[1] = R.drawable.image56;
			break;
		case 28:
			result[0] = R.drawable.image57;
			result[1] = R.drawable.image58;
			break;
		case 29:
			result[0] = 0;
			result[1] = 0;
			break;
		case 30:
			result[0] = R.drawable.image61;
			result[1] = R.drawable.image62;
			break;
		case 31:
			result[0] = R.drawable.image63;
			result[1] = R.drawable.image64;
			break;
		case 32:
			result[0] = 0;
			result[1] = 0;
			break;
		case 33:
			result[0] = R.drawable.image65;
			result[1] = R.drawable.image66;
			break;
		case 34:
			result[0] = R.drawable.image68;
			result[1] = R.drawable.image69;
			break;
		case 35:
			result[0] = R.drawable.image70;
			result[1] = R.drawable.image71;
			break;
		case 36:
			result[0] = R.drawable.image72;
			result[1] = R.drawable.image73;
			break;
		case 37:
			result[0] = R.drawable.image74;
			result[1] = R.drawable.image75;
			break;
		case 38:
			result[0] = R.drawable.image76;
			result[1] = R.drawable.image77;
			break;
		case 39:
			result[0] = R.drawable.image78;
			result[1] = R.drawable.image79;
			break;
		case 40:
			result[0] = R.drawable.image81;
			result[1] = R.drawable.image80;
			break;
		case 41:
			result[0] = R.drawable.image88;
			result[1] = 0;
			break;
		case 42:
			result[0] = R.drawable.image89;
			result[1] = R.drawable.image90;
			break;
		case 43:
			result[0] = R.drawable.image94;
			result[1] = R.drawable.image95;
			break;
		case 44:
			result[0] = R.drawable.image91;
			result[1] = R.drawable.image93;
			break;
		case 45:
			result[0] = R.drawable.image96;
			result[1] = R.drawable.image97;
			break;
		case 46:
			result[0] = R.drawable.image99;
			result[1] = R.drawable.image100;
			break;
		case 47:
			result[0] = R.drawable.image101;
			result[1] = R.drawable.image102;
			break;
		case 48:
			result[0] = R.drawable.image103;
			result[1] = R.drawable.image104;
			break;
		case 49:
			result[0] = R.drawable.image107;
			result[1] = 0;
			break;
		case 50:
			result[0] = R.drawable.image110;
			result[1] = R.drawable.image111;
			break;
		case 51:
			result[0] = R.drawable.image112;
			result[1] = R.drawable.image113;
			break;
		case 52:
			result[0] = R.drawable.image116;
			result[1] = R.drawable.image117;
			break;
		case 53:
			result[0] = R.drawable.image119;
			result[1] = 0;
			break;
		case 54:
			result[0] = R.drawable.image120;
			result[1] = R.drawable.image121;
			break;
		case 55:
			result[0] = R.drawable.image123;
			result[1] = R.drawable.image122;
			break;
		case 56:
			result[0] = R.drawable.image124;
			result[1] = R.drawable.image125;
			break;
		case 57:
			result[0] = R.drawable.image129;
			result[1] = R.drawable.image130;
			break;
		case 58:
			result[0] = R.drawable.image133;
			result[1] = R.drawable.image134;
			break;
		case 59:
			result[0] = R.drawable.image18;
			result[1] = R.drawable.image19;
			break;
		case 60:
			result[0] = R.drawable.image32;
			result[1] = R.drawable.image31;
			break;
		case 61:
			result[0] = R.drawable.image48;
			result[1] = R.drawable.image47;
			break;
		case 62:
			result[0] = R.drawable.image84;
			result[1] = R.drawable.image83;
			break;
		case 63:
			result[0] = R.drawable.image85;
			result[1] = R.drawable.image86;
			break;
		case 64:
			result[0] = R.drawable.image109;
			result[1] = 0;
			break;
		case 65:
			result[0] = R.drawable.image114;
			result[1] = R.drawable.image115;
			break;
		case 66:
			result[0] = R.drawable.image127;
			result[1] = R.drawable.image128;
			break;
		case 67:
			result[0] = R.drawable.image132;
			result[1] = R.drawable.image131;
			break;
		}

		return result;

	}

}
