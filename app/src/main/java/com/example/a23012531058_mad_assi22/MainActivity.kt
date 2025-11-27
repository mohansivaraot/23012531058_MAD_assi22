package com.example.simpleatm

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Change this to match your actual package name

class MainActivity : AppCompatActivity() {
    // 1. Declare Variables
    private var currentBalance = 1000.00 // Starting Balance
    private var tvBalance: TextView? = null
    private var tvStatus: TextView? = null
    private var etAmount: EditText? = null
    private var btnDeposit: Button? = null
    private var btnWithdraw: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Initialize Views (Connect Java to XML)
        tvBalance = findViewById<TextView?>(R.id.tvBalance)
        tvStatus = findViewById<TextView?>(R.id.tvStatus)
        etAmount = findViewById<EditText?>(R.id.etAmount)
        btnDeposit = findViewById<Button?>(R.id.btnDeposit)
        btnWithdraw = findViewById<Button?>(R.id.btnWithdraw)

        // 3. Set Initial Balance Text
        updateBalanceDisplay()

        // 4. Deposit Button Logic
        btnDeposit!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                handleTransaction(true)
            }
        })

        // 5. Withdraw Button Logic
        btnWithdraw!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                handleTransaction(false)
            }
        })
    }

    // Helper method to handle both Deposit and Withdrawal
    private fun handleTransaction(isDeposit: Boolean) {
        val amountString = etAmount!!.getText().toString()

        // Check if input is empty
        if (amountString.isEmpty()) {
            tvStatus!!.setText("Please enter an amount.")
            return
        }

        val amount = amountString.toDouble()

        if (isDeposit) {
            // DEPOSIT LOGIC
            currentBalance += amount
            tvStatus!!.setText("Deposit Successful: +$" + amount)
        } else {
            // WITHDRAW LOGIC
            if (amount > currentBalance) {
                tvStatus!!.setText("Insufficient Funds!")
                Toast.makeText(this, "Error: Insufficient Funds", Toast.LENGTH_SHORT).show()
                return  // Stop execution
            } else {
                currentBalance -= amount
                tvStatus!!.setText("Withdrawal Successful: -$" + amount)
            }
        }

        // Reset input and update screen
        etAmount!!.setText("")
        updateBalanceDisplay()
    }

    private fun updateBalanceDisplay() {
        tvBalance!!.setText(String.format("Balance: $%.2f", currentBalance))
    }
}