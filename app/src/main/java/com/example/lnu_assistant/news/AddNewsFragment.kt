package com.example.lnu_assistant.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lnu_assistant.R
import com.example.lnu_assistant.databinding.FragmentAddNewsBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date
import java.util.concurrent.TimeUnit

class AddNewsFragment : Fragment() {

    private lateinit var binding: FragmentAddNewsBinding

    private var userId: String = ""
    private var userName: String = ""

//    //Firebase
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var firebaseUser: FirebaseUser

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var storageReference: StorageReference
    private var collectionReference: CollectionReference = db.collection("Journal")
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(requireContext() as Activity, R.layout.fragment_add_news)

        storageReference = FirebaseStorage.getInstance().reference
//        firebaseAuth = Firebase.auth

        binding.apply {
            postProgressBar.visibility = View.INVISIBLE

//            if (News.instance != null) {
////                userId = JournalUser.instance!!.userId.toString()
////                userName = JournalUser.instance!!.userName.toString()
//
//                userId = firebaseAuth.currentUser!!.uid.toString()
//                userName = firebaseAuth.currentUser!!.displayName.toString()
//
//                postUsernameTextView.text = userName
//            }

            //Getting image from gallery
            postImageView.setOnClickListener {
                var i = Intent(Intent.ACTION_GET_CONTENT)
                i.setType("image/*")
                startActivityForResult(i, 1)
            }

            postSaveJournalButton.setOnClickListener {
                SaveJournal()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_news, container, false)
    }

    private fun SaveJournal() {
        var title: String = binding.postTitleEt.text.toString().trim()
        var description: String = binding.postDescriptionEt.text.toString().trim()

        binding.postProgressBar.visibility = View.VISIBLE

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri != null) {
            //Saving the path of images to Storage
            val filePath: StorageReference = storageReference
                .child("journal_images")
                .child("my_images_" + Timestamp.now().seconds)

            //Uploading images
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl
                        .addOnSuccessListener {
                            var imageUri: String = it.toString()
                            var time = Timestamp(Date())

                            //Creating the object of Journal
                            var news: News = News(
                                title,
                                description,
                                imageUri,
                                time,
                            )

                            collectionReference.add(news)
                                .addOnSuccessListener {
                                    binding.postProgressBar.visibility = View.INVISIBLE

                                    var intent = Intent(requireContext(), NewsFragment::class.java)
                                    startActivity(intent)
                                }
                        }
                }
                .addOnFailureListener {
                    binding.postProgressBar.visibility = View.INVISIBLE
                }
        }
        else {
            binding.postProgressBar.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                imageUri = data.data!! //getting actual image path
                binding.postImageView.setImageURI(imageUri) //showing image
            }
        }
    }
}