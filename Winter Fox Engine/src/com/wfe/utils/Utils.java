package com.wfe.utils;

import java.util.ArrayList;

public class Utils {

	public static String [] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
	
}
