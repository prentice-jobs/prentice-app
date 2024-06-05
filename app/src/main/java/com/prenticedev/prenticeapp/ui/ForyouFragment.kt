package com.prenticedev.prenticeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prenticedev.prenticeapp.databinding.FragmentForyouBinding

class ForyouFragment : Fragment() {
    private lateinit var binding: FragmentForyouBinding
//    private lateinit var mGoogleSignInClient: GoogleSignInClient
//    private lateinit var firebaseAuth: FirebaseAuth
//    private var pressedBack = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForyouBinding.inflate(inflater, container, false)
//        firebaseAuth = FirebaseAuth.getInstance()
//        firebaseAuth
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            Glide.with(this).load(firebaseUser.photoUrl).into(binding.imUser)
//            binding.nameUser.text = firebaseUser.displayName
//            binding.userUUID.text = firebaseUser.uid
//        } else {
//            Glide.with(this).load(R.drawable.baseline_profile_circle_24).into(binding.imUser)
//            binding.nameUser.text = "Anonymous"
//            binding.userUUID.text = "Empty"
//
//        }

//        mGoogleSignInClient =
//            GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
//        binding.btnLogout.setOnClickListener {
//            mGoogleSignInClient.signOut().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    firebaseAuth.signOut()
//                    val intent = Intent(requireContext(), SignInActivity::class.java)
//                    startActivity(intent)
//                    Toast.makeText(requireContext(), "Logout Successful", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        return binding.root
    }


}