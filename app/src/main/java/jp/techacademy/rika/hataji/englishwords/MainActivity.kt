package jp.techacademy.rika.hataji.englishwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
//import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.Toolbar
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mListView: ListView
    private lateinit var mTrackArrayList: ArrayList<Track>
    private lateinit var mWordArrayList: ArrayList<Word>
    private lateinit var mTrackListAdapter: TrackListAdapter
    private var mGenre = 0

    private var mGenreRef: DatabaseReference? = null
    private var mTimer: Timer? = null
    private var mHandler = Handler()

    private val mTrackListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val track = dataSnapshot.key as String
            val tracks = Track("Track" + track)
            mTrackArrayList.add(tracks)
            mTrackListAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onCancelled(p0: DatabaseError) {
        }
    }

    private val mWordListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val map = dataSnapshot.value as Map<String, String>
            val num = map["num"] ?: ""
            val word = map["word"] ?: ""
            val wordClass = map["wordClass"] ?: ""
            val wordMean = map["wordMean"] ?: ""
            val sentence = map["sentence"] ?: ""
            val sentenceMean = map["sentenceMean"] ?: ""

            val wordList = Word(num, word, wordClass, wordMean, sentence, sentenceMean)
            mWordArrayList.add(wordList)
            //mTrackListAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onCancelled(p0: DatabaseError) {

        }
    }

    private val mEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val map = dataSnapshot.value as Map<String, String>
            val num = map["num"] ?: ""
            val word = map["word"] ?: ""
            val wordClass = map["wordClass"] ?: ""
            val wordMean = map["wordMean"] ?: ""
            val wordSentence = map["wordSentence"] ?: ""
            val wordSentenceMean = map["wordSentenceMean"] ?: ""

//            val answerArrayList = ArrayList<Answer>()
//            val answerMap = map["answers"] as Map<String, String>?
//            if (answerMap != null) {
//                for (key in answerMap.keys) {
//                    val temp = answerMap[key] as Map<String, String>
//                    val answerBody = temp["body"] ?: ""
//                    val answerName = temp["name"] ?: ""
//                    val answerUid = temp["uid"] ?: ""
//                    val answer = Answer(answerBody, answerName, answerUid, key)
//                    answerArrayList.add(answer)
//                }
//            }

            val words = Word(num, word, wordClass, wordMean, wordSentence, wordSentenceMean)
            mWordArrayList.add(words)
            mTrackListAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onCancelled(p0: DatabaseError) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ListViewの準備
        mListView = findViewById(R.id.listView)
        mTrackListAdapter = TrackListAdapter(this)
        mTrackArrayList = ArrayList<Track>()
        mTrackListAdapter.setQuestionArrayList(mTrackArrayList)
        mListView.adapter = mTrackListAdapter
        mTrackListAdapter.notifyDataSetChanged()

        mListView.setOnItemClickListener { parent, view, position, id ->
            //mTimer = Timer()

            // Firebase
            mDatabaseReference = FirebaseDatabase.getInstance().reference

            mGenreRef = mDatabaseReference.child(ContentsPATH).child(WordsPATH)
            mGenreRef!!.addChildEventListener(mWordListener)
            
            // WordActivityのインスタンスを渡して単語スラッシュ画面を起動する
            val word_intent = Intent(applicationContext, WordActivity::class.java)
            word_intent.putExtra("word", mTrackArrayList[position])
            startActivity(word_intent)
        }

        // Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mGenreRef = mDatabaseReference.child(ContentsPATH).child(WordsPATH)
        mGenreRef!!.addChildEventListener(mTrackListener)
    }
}