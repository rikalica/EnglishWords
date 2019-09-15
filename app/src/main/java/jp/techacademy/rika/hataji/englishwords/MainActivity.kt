package jp.techacademy.rika.hataji.englishwords

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toolbar
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mTrackReference: DatabaseReference
    private lateinit var mListView: ListView
    private lateinit var mTrackArrayList: ArrayList<Track>
    private lateinit var mWordArrayList: ArrayList<Word>
    private val mTrackArrayMap: HashMap<String,ArrayList<Word>> = HashMap<String,ArrayList<Word>>()
    private lateinit var mTrackListAdapter: TrackListAdapter
    private lateinit var trackNum : String

    private var mGenreRef: DatabaseReference? = null

    private val mTrackListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val track = dataSnapshot.key as String
            val tracks = Track("Track" + track, track)
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
            val map = dataSnapshot.value as List<Map<String, String>>

            for(wordUnity in map) {
                if (wordUnity != null) {
                    var num = wordUnity["num"] ?: ""
                    var word = wordUnity["word"] ?: ""
                    var wordClass = wordUnity["wordClass"] ?: ""
                    var wordMean = wordUnity["wordMean"] ?: ""
                    var sentence = wordUnity["sentence"] ?: ""
                    var sentenceMean = wordUnity["sentenceMean"] ?: ""

                    var wordList = Word(num, word, wordClass, wordMean, sentence, sentenceMean)
                    mWordArrayList.add(wordList)
                    //mTrackListAdapter.notifyDataSetChanged()
                }
                mTrackArrayMap.put(trackNum, mWordArrayList)
            }
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
        mWordArrayList = ArrayList<Word>()
        mTrackListAdapter.notifyDataSetChanged()

        // Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mGenreRef = mDatabaseReference.child(ContentsPATH).child(WordsPATH)
        mGenreRef!!.addChildEventListener(mTrackListener)

        //データを事前に保存
        for(track in mTrackArrayList){
            trackNum = track.num
            // Firebase
            mTrackReference = FirebaseDatabase.getInstance().reference
            mGenreRef = mTrackReference.child(ContentsPATH).child(WordsPATH).child(track.num)
            mGenreRef!!.addChildEventListener(mWordListener)
        }

        mListView.setOnItemClickListener { parent, view, position, id ->
            // WordActivityのインスタンスを渡して単語スラッシュ画面を起動する
            val word_intent = Intent(applicationContext, WordActivity::class.java)
            word_intent.putExtra("word", mTrackArrayList[position])
            word_intent.putExtra("wordList", mWordArrayList)
            startActivity(word_intent)
        }
    }
}