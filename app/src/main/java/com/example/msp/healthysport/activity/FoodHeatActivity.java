package com.example.msp.healthysport.activity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msp.healthysport.R;
import com.example.msp.healthysport.base.BaseActivity;
import com.example.msp.healthysport.model.FoodMessage;
import com.example.msp.healthysport.model.FoodType;
import com.example.msp.healthysport.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodHeatActivity extends BaseActivity {

    private int[] ids;//图片资源id数组
    private int sign = -1;
    private List<FoodType> foodList;
    private String[] foodTypeArray;
    private ExpandableListView listView;
    private Bitmap[] bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        listView = findViewById(R.id.food_list);
    }

    @Override
    protected void setActivityTitle() {
        setTitle("食物热量对照表");
    }

    @Override
    protected void setViewLayoutResouce() {
        setContentView(R.layout.activity_food_heat);
    }

    @Override
    protected void initValues() {

        ids = new int[]{R.mipmap.mrkj_gu,R.mipmap.mrkj_cai,
                R.mipmap.mrkj_guo, R.mipmap.mrkj_rou, R.mipmap.mrkj_dan,
                R.mipmap.mrkj_yv, R.mipmap.mrkj_nai, R.mipmap.mrkj_he,
                R.mipmap.mrkj_jun, R.mipmap.you};

        bitmaps = new Bitmap[ids.length];

        for(int i = 0;i < ids.length;i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),ids[i]);
        }

        foodTypeArray = new String[] {
                "五谷类","蔬菜类","水果类","肉类","蛋类","水产类","奶类","饮料类",
                "菌澡类","油脂类"};
        foodList = new ArrayList<>();

        DBHelper dbHelper = new DBHelper();
        Cursor cursor = dbHelper.selectAllDataOfTable("hot");
//        Log.e("数据数量", cursor.getCount() + "");
//        int j = 0;
        for (int i = 0; i < 10; i++) {
            FoodType foodType = null;
            List<FoodMessage> foods = null;
            int counts = 1;
            while (cursor.moveToNext()) {
//                Log.e("计数", (++j) + "");
//                Log.e("counts", counts + "");
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String hot = cursor.getString(cursor.getColumnIndex("hot"));
                String type_name = cursor.getString(cursor.getColumnIndex("type_name"));
                if (counts == 1) {
                    foodType = new FoodType();
                    foods = new ArrayList<>();
                    foodType.setFoodType(type_name);
//                    Log.e("type_name", type_name + "");

                }
                FoodMessage foodMessage = new FoodMessage();
                foodMessage.setFoodName(name);
                foodMessage.setHot(hot);
                foods.add(foodMessage);
                foodType.setFoodList(foods);
                if (counts == 20) {
                    foodList.add(foodType);
                    break;
                }
                counts++;
            }
        }
        cursor.close();
        Log.e("数据的长度", foodList.size() + "");
//        for (FoodType foodType : food_list) {
//            Log.e("食物类型", foodType.getFood_type() + "");
//            Log.e("食物数量",foodType.getFood_list().size() + "");
//        }
    }

    @Override
    protected void setViewsListener() {

    }

    @Override
    protected void setViewsFunction() {
        FoodAdapter foodAdapter = new FoodAdapter();
        listView.setAdapter(foodAdapter);

    }

    class FoodAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return foodList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return foodList.get(i).getFoodList().size();
        }

        @Override
        public Object getGroup(int i) {
            return foodList.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return foodList.get(i).getFoodList().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder holder;

            if(convertView == null) {
                holder = new GroupViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.group_item,null);
                holder.imageView = convertView.findViewById(R.id.group_image);
                holder.title = convertView.findViewById(R.id.group_title);
                convertView.setTag(holder);
            } else {
                holder = (GroupViewHolder) convertView.getTag();
            }

            holder.imageView.setImageBitmap(bitmaps[groupPosition]);
            holder.title.setText(foodTypeArray[groupPosition]);

            return convertView;
        }

        @Override
        public View getChildView(int groupPostion, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder holder;

            if(convertView == null) {
                holder = new ChildViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.child_item,null);
                holder.name = convertView.findViewById(R.id.food_name);
                holder.hot = convertView.findViewById(R.id.food_hot);
                convertView.setTag(holder);
            }else {
                holder = (ChildViewHolder) convertView.getTag();
            }

            FoodMessage foodMessage = foodList.get(groupPostion).getFoodList().get(childPosition);
            holder.name.setText(foodMessage.getFoodName());
            holder.hot.setText(foodMessage.getHot()+"千卡/克");

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    class GroupViewHolder {
        ImageView imageView;
        TextView title;
    }

    class ChildViewHolder {
        TextView name,hot;
    }
}
