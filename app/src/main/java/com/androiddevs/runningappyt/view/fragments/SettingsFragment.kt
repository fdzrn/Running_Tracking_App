package com.androiddevs.runningappyt.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androiddevs.runningappyt.R
import com.androiddevs.runningappyt.constant.ConstantsValue.KEY_NAME
import com.androiddevs.runningappyt.constant.ConstantsValue.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPreferences()
        btnApplyChanges.setOnClickListener {
            val success = applyChangeToSharedPreferences()
            if (success) {
                Snackbar.make(view, "Changes saved successfully", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "Please fill out all the Fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun applyChangeToSharedPreferences(): Boolean {
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty()) return false

        sharedPreferences.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()
        val toolbarText = "Let's Go $nameText"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }

    private fun loadFieldsFromSharedPreferences() {
        val name = sharedPreferences.getString(KEY_NAME,"")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 62f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }
}