package jp.techacademy.rika.hataji.englishwords

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

class TrackListAdapter(context: Context) : BaseAdapter() {
    private var mLayoutInflater: LayoutInflater
    private var mWordArrayList = ArrayList<Word>()

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return mWordArrayList.size
    }

    override fun getItem(position: Int): Any {
        return mWordArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_tracks, parent, false)
        }

        val titleText = convertView!!.findViewById<View>(R.id.titleTextView) as TextView
        titleText.text = mWordArrayList[position].num

//        val nameText = convertView.findViewById<View>(R.id.nameTextView) as TextView
//        nameText.text = mWordArrayList[position].word

//        val resText = convertView.findViewById<View>(R.id.resTextView) as TextView
//        resText.text = mWordArrayList[position].wordClass

//        val resText = convertView.findViewById<View>(R.id.resTextView) as TextView
//        resText.image = mWordArrayList[position].wordMean


        return convertView
    }

    fun setQuestionArrayList(wordArrayList: ArrayList<Word>) {
        mWordArrayList = wordArrayList
    }
}