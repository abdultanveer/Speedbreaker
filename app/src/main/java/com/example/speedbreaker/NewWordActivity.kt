package com.example.speedbreaker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for entering a word.
 */

class NewWordActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        val editidView = findViewById<EditText>(R.id.edit_id)



        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            var word2 = Word(2,"12345",
                "123456,12345","12345",
                "1234","1234","12345","12345","12345","12345",)
            /*  if (TextUtils.isEmpty(editidView.text)) {
                  setResult(Activity.RESULT_CANCELED, replyIntent)
              } else {*/
            val word = editidView.text.toString()
            //  replyIntent.putExtra(EXTRA_REPLY, word)
            replyIntent.putExtra("myword",word2)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }


    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
