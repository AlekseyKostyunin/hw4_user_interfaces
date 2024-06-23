package com.alekseykostyunin.hw4_user_interfaces

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.alekseykostyunin.hw4_user_interfaces.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intProgress = Random().nextInt(100)
        binding.progress.progress = intProgress
        binding.countScores.text = "$intProgress/100"

        checkButtonSave()
        checkNotification(false)
        changeEditNameText()
        changeEditPhoneText()
        changeChoiceNotifications()
        changeRadioButton()
        changeNotificationAuthorization()

    }

    private fun changeEditNameText() {
        binding.name.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                binding.textInputName.isErrorEnabled = text.length > 40
            }
            checkButtonSave()
        }
    }

    private fun changeNotificationAuthorization() {
        binding.checkbox1.setOnCheckedChangeListener { _, _ ->
            checkButtonSave()
        }

        binding.checkbox2.setOnCheckedChangeListener { _, _ ->
            checkButtonSave()
        }
    }

    private fun changeRadioButton() {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            checkButtonSave()
        }
    }

    private fun changeEditPhoneText() {
        binding.telephone.doOnTextChanged { _, _, _, _ ->
            checkButtonSave()
        }
    }

    private fun changeChoiceNotifications() {
        binding.receiveNotifications.setOnCheckedChangeListener { _, isChecked ->
            checkNotification(isChecked)
            checkButtonSave()
        }
    }

    private fun checkNotification(isChecked: Boolean) {
        binding.checkbox1.isEnabled = isChecked
        binding.checkbox2.isEnabled = isChecked
        if (!isChecked) {
            binding.checkbox1.isChecked = false
            binding.checkbox2.isChecked = false
        }
        checkButtonSave()
    }

    private fun checkInputName(): Boolean {
        return !(binding.textInputName.isErrorEnabled || binding.name.text.isNullOrEmpty())
    }

    private fun checkRadioButton(): Boolean {
        if (binding.radioM.isChecked || binding.radioW.isChecked) return true
        return false
    }

    private fun checkNotifications(): Boolean {
        if (binding.receiveNotifications.isChecked && binding.checkbox1.isChecked
            || binding.receiveNotifications.isChecked && binding.checkbox2.isChecked) {
            return true
        } else if (!binding.receiveNotifications.isChecked) {
            return true
        }
        return false
    }

    private fun checkButtonSave() {
        binding.buttonSave.isEnabled =
            checkInputName() && !binding.telephone.text.isNullOrEmpty() && checkRadioButton() && checkNotifications()
        if (binding.buttonSave.isEnabled) pressButtonSave()
    }

    private fun pressButtonSave() {
        binding.buttonSave.setOnClickListener {
            showSnackbar(resources.getString(R.string.save_info))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

