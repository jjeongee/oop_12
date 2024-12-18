package com.example.toastout.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.toastout.R
import com.example.toastout.data.repository.Authentication_Repository
import com.example.toastout.ui.home.Home_Fragment

class Login_Fragment : Fragment() {

    private lateinit var viewModel: Auth_ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_login 레이아웃을 Inflate
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Authentication_Repository()
        viewModel = ViewModelProvider(requireActivity(),Auth_ViewModelFactory(repository))[Auth_ViewModel::class.java]

        //xml 요소와 연결
        val loginButton : Button = view.findViewById(R.id.login_button)
        val registerButton : Button = view.findViewById(R.id.register_button)
        val emailInput : EditText = view.findViewById(R.id.email_input)
        val passwordInput : EditText = view.findViewById(R.id.password_input)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.loginAuth(email,password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner){ result ->
            if(result.isSuccess){
                //로그인에 성공하면 homefragment로 전환
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Home_Fragment())
                    .commit()
            } else{
                Toast.makeText(requireContext(),"로그인 실패 : ${result.exceptionOrNull()?.message}",Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.registerAuth(email,password)
        }

        viewModel.registerResult.observe(viewLifecycleOwner){ result ->
            if(result.isSuccess){
                //회원가입 성공 메세지 토스트로 띄우기
                Toast.makeText(requireContext(),"성공적으로 회원가입 되었습니다.",Toast.LENGTH_SHORT).show()

            } else{
                Toast.makeText(requireContext(),"회원가입 실패 : ${result.exceptionOrNull()?.message}",Toast.LENGTH_SHORT).show()
            }
        }
    }
}