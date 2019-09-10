package jp.techacademy.rika.hataji.englishwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ListView
import android.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_word.*
import java.util.*

class WordActivity : AppCompatActivity() {
    private lateinit var mWordArrayList: ArrayList<Word>

    private var mTimer: Timer? = null
    private var mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        val extras = intent.extras
        val track = extras.get("word") as Track
        var num = 0
        var viewNum = 0

        // タイマーの作成
        mTimer = Timer()

        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                mHandler.post {
                    when (viewNum) {
                        0 -> {
                            two.setVisibility(View.GONE)
                            one.setVisibility(View.VISIBLE)
                            word.setVisibility(View.VISIBLE)
                            line_vertical_center.setVisibility(View.VISIBLE)
                        }
                        1 -> {
                            two.setVisibility(View.GONE)
                            one.setVisibility(View.VISIBLE)
                            word.setVisibility(View.VISIBLE)
                            line_vertical_center.setVisibility(View.VISIBLE)
                            word_mean.setVisibility(View.VISIBLE)
                        }
                        2 -> {
                            one.setVisibility(View.GONE)
                            two.setVisibility(View.VISIBLE)
                            sentence.setVisibility(View.VISIBLE)
                            line_horizontal_center.setVisibility(View.VISIBLE)
                        }
                        3 -> {
                            one.setVisibility(View.GONE)
                            two.setVisibility(View.VISIBLE)
                            sentence.setVisibility(View.VISIBLE)
                            line_horizontal_center.setVisibility(View.VISIBLE)
                            sentence_mean.setVisibility(View.VISIBLE)

                            viewNum = 0
                            num++
                        }
                    }
                    viewNum += 1

                    if(mWordArrayList.size >= num) {
                        mTimer!!.cancel()
                    }
                }
            }
        }, 10000, 10000) // 最初に始動させるまで 100ミリ秒、ループの間隔を 100ミリ秒 に設定
    }
}