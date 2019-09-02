package jp.techacademy.rika.hataji.englishwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.Toolbar
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mListView: ListView
    private lateinit var mTrackArrayList: ArrayList<Track>
    private lateinit var mWordArrayList: ArrayList<Word>
    private lateinit var mAdapter: TrackListAdapter
    private var mGenre = 0

    private var mGenreRef: DatabaseReference? = null

    private val mTrackListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val track = dataSnapshot.key as String

            Log.d("AAA", "AAA")
            val tracks = Track("Track" + track)
            mTrackArrayList.add(tracks)
            mAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
            Log.d("AAA", "AAA")
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            Log.d("AAA", "AAA")

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            Log.d("AAA", "AAA")

        }

        override fun onCancelled(p0: DatabaseError) {
            Log.d("AAA", "AAA")

        }
    }

    private val mWordListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            val map = dataSnapshot.value as Map<String, String>
            val num = map["num"] ?: ""
            val word = map["word"] ?: ""
            val wordClass = map["wordClass"] ?: ""
            val wordMean = map["wordMean"] ?: ""

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

            val question = Word(num, word, wordClass, wordMean)
            mWordArrayList.add(question)
            mAdapter.notifyDataSetChanged()
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

            val question = Word(num, word, wordClass, wordMean)
            mWordArrayList.add(question)
            mAdapter.notifyDataSetChanged()
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
        mAdapter = TrackListAdapter(this)
        mTrackArrayList = ArrayList<Track>()
        mAdapter.setQuestionArrayList(mTrackArrayList)
        mListView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()

        // Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mGenreRef = mDatabaseReference.child(ContentsPATH).child(WordsPATH)
        mGenreRef!!.addChildEventListener(mTrackListener)
    }
}