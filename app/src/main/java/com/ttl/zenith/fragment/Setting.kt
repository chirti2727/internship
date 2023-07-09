package com.ttl.zenith.fragment
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ttl.zenith.R

class Setting: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}