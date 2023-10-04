package vn.edu.usth.demoapp.fragment_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.showLoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.interface_controller.FoodCallback;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.network_controller.RecipeController;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

public class ExploreFragment extends Fragment {

    private List<Food> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Dialog dialog = showLoadingDialog(requireContext());

        FoodAdapter foodAdapter;
        RecyclerView rcvFood;
        View mView;
        mView = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvFood = mView.findViewById(R.id.rcv_food);
        foodAdapter = new FoodAdapter(requireContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        getListFood(new FoodCallback() {
            @Override
            public void onFoodListReceived(List<Food> foodList) {
                foodAdapter.setData(foodList);
                if(dialog != null)
                    dialog.dismiss();
                rcvFood.setAdapter(foodAdapter);
            }
            @Override
            public void onError(VolleyError error) {
                list = null;
            }
        });
        return mView;
    }

    private void getListFood(FoodCallback callback) {
        RecipeController recipeController = new RecipeController();
        recipeController.getExploreList(requireContext(), new FoodListCallback() {
            @Override
            public void onSuccess(List<Food> result) {
                callback.onFoodListReceived(result);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }






}