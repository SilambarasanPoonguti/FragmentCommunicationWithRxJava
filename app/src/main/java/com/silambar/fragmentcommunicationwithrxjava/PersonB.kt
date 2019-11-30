package com.silambar.fragmentcommunicationwithrxjava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_person_b.*

class PersonB : Fragment() {

    private val messages = ArrayList<String>()
    private var message: String = ""
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_b, container, false)
    }

    override fun onResume() {
        super.onResume()

        sendBtn.setOnClickListener {
            val msg = msgBox.text.toString()
            if (msg.isNotEmpty() && !msg.contentEquals(message)) {
                RxBus.instance?.personASubject?.onNext(msg)
                msgBox.setText("")
                message = msg
            }
        }

        messageAdapter = MessageAdapter(messages)

        val lManager = LinearLayoutManager(context).apply { reverseLayout = true }
        msgListB.apply {
            layoutManager = lManager
            adapter = messageAdapter
        }

        RxBus.instance?.personBSubject?.subscribe(object : Observer<String> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(msg: String) {
                messages.add(msg)
                messageAdapter.notifyItemInserted(messages.size)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        })
    }
}