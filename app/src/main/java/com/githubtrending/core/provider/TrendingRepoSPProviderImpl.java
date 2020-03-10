package com.githubtrending.core.provider;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by kevin.adhitama on 10/03/20.
 */
public class TrendingRepoSPProviderImpl implements TrendingRepoSPProvider {
    private final String PREF_KEY_REPO_LIST_TS_MS = "repo_list_ts_ms";
    private final String PREF_KEY_REPO_LIST_JSON = "repo_list_json";
    private Context mContext;

    public TrendingRepoSPProviderImpl(Context context) {
        mContext = context;
    }

    @Override
    public String getQ2HCachedTrendingRepoListJSON() {
        SharedPreferences sp = getSharedPreferences();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sp.getLong(PREF_KEY_REPO_LIST_TS_MS, 0));
        cal.add(Calendar.HOUR, 2);
        long cachedTSMillis = cal.getTimeInMillis();
        long currentTSMillis = Calendar.getInstance().getTimeInMillis();

        if (currentTSMillis > cachedTSMillis) {
            return "";
        }
        return sp.getString(PREF_KEY_REPO_LIST_JSON, "");
    }

    @Override
    public void setQ2HCachedTrendingRepoListJSON(String stringJSON) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_KEY_REPO_LIST_JSON, stringJSON);
        editor.putLong(PREF_KEY_REPO_LIST_TS_MS, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        String CACHED_TRENDING_REPO_LIST = "pref_cached_trending_repo_list";
        return mContext.getSharedPreferences(CACHED_TRENDING_REPO_LIST, Context.MODE_PRIVATE);
    }
}
