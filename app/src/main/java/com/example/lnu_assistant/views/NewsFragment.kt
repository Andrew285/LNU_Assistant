package com.example.lnu_assistant.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lnu_assistant.R
import com.example.lnu_assistant.databinding.FragmentNewsBinding
import com.example.lnu_assistant.model.News
import com.example.lnu_assistant.views.adapters.NewsAdapter
import com.example.lnu_assistant.views.interfaces.IFragmentNavigator
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class NewsFragment : Fragment(), IFragmentNavigator {

    private lateinit var binding: FragmentNewsBinding

    //Firebase Reference
//    lateinit var firebaseAuth: FirebaseAuth
//    lateinit var firebaseUser: FirebaseUser
    var db = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference: CollectionReference = db.collection("Journal")

    lateinit var newsList: MutableList<News>
    lateinit var newsAdapter: NewsAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        //Firebase
//        firebaseAuth = Firebase.auth
//        firebaseUser = firebaseAuth.currentUser!!

        //RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireActivity())

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_addNewsFragment)
        }

        // Posts arraylist
        newsList = arrayListOf()


        newsAdapter = NewsAdapter(requireActivity(), newsList)
        binding.recyclerView.adapter = newsAdapter
    }

    override fun onStart() {
        super.onStart()

        collectionReference
//            .whereEqualTo("title", firebaseUser.uid)
            .get()
            .addOnSuccessListener {
                Log.i("TAGY", "Get is successful")
                if (!it.isEmpty) {
                    for (document in it) {
                        Log.i("TAGY", "It is not empty")
                        val news = News(
                            document.data["title"].toString(),
                            document.data["description"].toString(),
                            document.data["imageUrl"].toString(),
                            document.data["timeAdded"] as Timestamp
                        )

                        newsList.add(news)
                    }


                    newsAdapter = NewsAdapter(requireContext(), newsList)
                    binding.recyclerView.adapter = newsAdapter
                    newsAdapter.notifyDataSetChanged()
                }
                else {
                    Log.i("TAGY", "It is empty")
                    binding.textView.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    override fun title(): String {
        return getString(R.string.toolbar_newsFragment)
    }
}