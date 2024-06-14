package com.metropolitan.cs330_pz_4096.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.metropolitan.cs330_pz_4096.R
import com.metropolitan.cs330_pz_4096.activities.MealActivity
import com.metropolitan.cs330_pz_4096.databinding.FragmentHomeBinding
import com.metropolitan.cs330_pz_4096.pojo.Meal
import com.metropolitan.cs330_pz_4096.pojo.MealList
import com.metropolitan.cs330_pz_4096.retrofit.RetrofitInstance
import com.metropolitan.cs330_pz_4096.videoModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal

    companion object {
        const val MEAL_ID = "com.metropolitan.cs330_pz_4096.fragments.idMeal"
        const val MEAL_NAME = "com.metropolitan.cs330_pz_4096.fragments.nameMeal"
        const val MEAL_THUMB = "com.metropolitan.cs330_pz_4096.fragments.mealThumb"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()


    }

    private fun onRandomMealClick() {
        binding.randomRecipeCard.setOnClickListener() {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomRecipe)
                this.randomMeal = meal
            })
    }
}

