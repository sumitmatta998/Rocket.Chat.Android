package chat.rocket.app.db.dao;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import chat.rocket.app.db.DBManager;

/**
 * Created by julio on 24/11/15.
 */
public class LoginServiceConfiguration {
    //TODO: create an enum or something..
    /*
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"53Mwt8NnhasL55cnN\",\"fields\":{\"service\":\"google\",\"clientId\":\"662030055877-uma7qar29ji84gopa740fasv2f6rfj1v.apps.googleusercontent.com\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"5Dq9unPcc4MGJaToS\",\"fields\":{\"service\":\"gitlab\",\"clientId\":\"ad37be86b2fe45ab494ea51cffffa673c89c240e9fa6addfbedd06a587707731\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"JMBQZo25cKkcgkzNB\",\"fields\":{\"service\":\"github\",\"clientId\":\"886a7b2a1fb067b10d3a\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"RuLCNdtyeL5aGAMGP\",\"fields\":{\"service\":\"twitter\",\"consumerKey\":\"njl2I82AXrOo0YftZyKHyC8aV\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"hN7c6shuXY47vfpLk\",\"fields\":{\"service\":\"meteor-developer\",\"clientId\":\"LDGYjcX2knskEP8HR\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"uBpyHAapf7Ajshi22\",\"fields\":{\"service\":\"facebook\",\"appId\":\"835103589938459\"}}"]
 collection\":\"meteor_accounts_loginServiceConfiguration\",\"id\":\"wixnKwF7wHxm8jg4D\",\"fields\":{\"service\":\"linkedin\",\"clientId\":\"77j932xbl3gl62\"}}"]
*/

    private static final String collectionName = "meteor_accounts_loginServiceConfiguration";

    public static String query(String service, String identifier) {
        Cursor cursor = DBManager.getInstance().query(CollectionDAO.TABLE_NAME, null, CollectionDAO.COLUMN_COLLECTION_NAME + "=?", new String[]{collectionName});
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        String value = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String values = cursor.getString(cursor.getColumnIndex(CollectionDAO.COLUMN_FIELDS));
                    Map<String, String> map = new Gson().fromJson(values, mapType);
                    if (service.equals(map.get("service"))) {
                        value = map.get(identifier);
                        break;
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return value;
    }
}
