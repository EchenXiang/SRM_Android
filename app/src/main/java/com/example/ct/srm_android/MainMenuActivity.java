package com.example.ct.srm_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ct.srm_android.Logistics.Delivery_ListViewActivity;
import com.example.ct.srm_android.Logistics.Logistics_MainActivity;
import com.example.ct.srm_android.Performance.*;
import com.example.ct.srm_android.Plan.PurchasePlanMainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.example.ct.srm_android.Notice.*;
import Tools.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.ImageAdapter;
import Tools.RoundImageView;

public class MainMenuActivity extends AppCompatActivity {
    private Button jump_SlidingMenu_button;
    private LuoGridView gridview;
    private List<String> datas;
   /* private MyAdapter adapter;*/
    private ImageView iv;
    private User user;

    private RoundImageView roundImageView;
    private ImageView user_imag;
    private GridView gview;
    private ListView personal_listView;
    private List<Map<String, Object>> LeftMenuData;
    private List<Map<String, Object>> Menudata_list;
    private GridAdapter gridAdapter;
    private SimpleAdapter sim_adapter1;
    private TextView userName;
    private int mScreenWidth = 0;
    private int mItemWidth = 0;
    private DisplayMetrics dm;
    private int[] icon = {R.drawable.order_manager, R.drawable.stock_manager,
            R.drawable.procurement_plan, R.drawable.notice,R.drawable.performance,R.drawable.logistics_management,R.drawable.contract_management,R.drawable.tendering_and_bidding
    ,R.drawable.quality_management,R.drawable.data_analysis,R.drawable.financial_management,R.drawable.xunyuan};

    private int[] left_menu_icon  = {R.drawable.ic_settings_grey, R.drawable.ic_exit_to_app_grey};
    private String[] left_menu_item_text = {"设置","退出登录"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TitleSet. getTitleBar(this,"我的自定义标题栏");

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

        setSupportActionBar(toolbar);




        setContentView(R.layout.activity_main_menu);

        user = (User)getApplication();

        gridview = (LuoGridView) findViewById(R.id.GridView);
       /* iv = (ImageView) findViewById(R.id.iv);*/
       /* initData();*/
        Menudata_list = new ArrayList<Map<String, Object>>();
        Menudata_list = getMenuData();
        gridAdapter = new GridAdapter(getApplicationContext());
        gridview.setAdapter(gridAdapter);
        gridview.setClipToPadding(false);
        gridview.setSelected(true);
        gridview.setSelection(0);
        gridview.setSelector(android.R.color.transparent);
        gridview.setMySelector(R.drawable.myborder);
        gridview.setMyScaleValues(1.3f, 1.3f);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(MainMenuActivity.this,OrderSelectActivity.class);
                        startActivity(intent);
                        break;
                    case 1:

                        Intent intent1 = new Intent(MainMenuActivity.this,stocks_MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:

                        Intent intent2 = new Intent(MainMenuActivity.this, PurchasePlanMainActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:

                        Intent intent3 = new Intent(MainMenuActivity.this, Notice_MainActivity.class);
                        startActivity(intent3);
                        break;

                    case 4:

                        Intent intent4 = new Intent(MainMenuActivity.this,PerformanceActivity.class);

                        startActivity(intent4);
                        break;

                    case 5:

                        Intent intent5 = new Intent(MainMenuActivity.this, Logistics_MainActivity.class);
                        startActivity(intent5);
                        break;

                }
            }
        });






        // configure the SlidingMenu
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);

        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        /*menu.setMenu(R.layout.layout_left_menu);*/
        menu.setMenu(R.layout.layout_left_menu);
        userName = (TextView)findViewById(R.id.left_menu_userName) ;
        userName.setText(user.getuserName());
        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {

            }
        });
        personal_listView = (ListView) findViewById(R.id.personal_listView);

        LeftMenuData = getLeftMenuData();
        /*LeftMenuAdapter adapter = new LeftMenuAdapter(MainMenuActivity.this);*/
        String[] from1 = {"left_menu_image","left_menu_text"};
        int[] to1 = {R.id.left_menu_image,R.id.left_menu_text};
        sim_adapter1= new SimpleAdapter(this, LeftMenuData, R.layout.left_menu_listview_item, from1, to1);
        personal_listView.setAdapter(sim_adapter1);

        personal_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**用户侧滑界面Listview响应
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        SharedPreferences preference = getSharedPreferences("login_info",
                                MODE_PRIVATE);
                        // 获取editor
                        SharedPreferences.Editor editor = preference.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        break;
                }
            }
        });
      /*  gview = (GridView) findViewById(R.id.GridView);
        Menudata_list = new ArrayList<Map<String, Object>>();
        Menudata_list = getMenuData();
        gridAdapter = new GridAdapter(getApplicationContext());
        gview.setAdapter(gridAdapter);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent intent = new Intent(MainMenuActivity.this,OrderSelectActivity.class);
                        startActivity(intent);
                        break;
                    case 1:

                        Intent intent1 = new Intent(MainMenuActivity.this,stocks_MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:

                        Intent intent2 = new Intent(MainMenuActivity.this, PurchasePlanMainActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:

                        Intent intent3 = new Intent(MainMenuActivity.this, Notice_MainActivity.class);
                        startActivity(intent3);
                        break;

                    case 4:

                        Intent intent4 = new Intent(MainMenuActivity.this,PerformanceActivity.class);
                        startActivity(intent4);
                        break;

                }
            }
        });
        gview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Log.d("zonesView", "onItemSelected");
                GridAdapter zonesImageAdapter = (GridAdapter) gview.getAdapter();
                zonesImageAdapter.notifyDataSetChanged(arg2);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });*/


        user_imag = (ImageView) findViewById(R.id.user_image);


        user_imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showMenu();
                /*personal_listView = (ListView) findViewById(R.id.personal_listView);
                LeftMenuData = build_LeftMenu_Data();
                LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(MainMenuActivity.this);
                personal_listView.setAdapter(leftMenuAdapter);*/

            }
        });

        roundImageView = (RoundImageView)findViewById(R.id.roundImage_two_border);
        roundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("SRM_Android");
        setSupportActionBar(toolbar);
    }

    public List<Map<String, Object>> getMenuData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", icon[i]);

            Menudata_list.add(map);
        }

        return Menudata_list;
    }


    public List<Map<String, Object>> getLeftMenuData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < left_menu_icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("left_menu_image", left_menu_icon[i]);
            map.put("left_menu_text", left_menu_item_text[i]);
            data.add(map);
        }

        return data;
    }

    public class LeftMenuAdapter extends BaseAdapter {
            private LayoutInflater inflater;
            private Context mContext = null;

            public LeftMenuAdapter(Context context) {
                mContext = context;
                this.inflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return LeftMenuData.size();
            }

            @Override
            public Object getItem(int position) {
                //TODO Auto-generated method stub
                return LeftMenuData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }



            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                Holder holder;          //自定义一个Holder 把一个Item中的View绑定起来
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    view = inflater.inflate(R.layout.left_menu_listview_item, null);
                    holder = new Holder(view);
                    view.setTag(holder);
                } else {
                    view = convertView;
                    holder = (Holder) view.getTag();
                }
                holder.item_image.setBackgroundResource((Integer) LeftMenuData.get(position).get("left_menu_image"));
                holder.item_title.setText((String) LeftMenuData.get(position).get("left_menu_text"));

                return view;
            }

        public class Holder {
            public ImageView item_image;
            public TextView item_title;

            public Holder(View view) {
                item_image = (ImageView) view.findViewById(R.id.left_menu_image);
                item_title = (TextView) view.findViewById(R.id.left_menu_text);




                }

            }
        }


    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext = null;

        public GridAdapter(Context context) {
            mContext = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int position) {
            //TODO Auto-generated method stub
            return icon[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder2 holder;          //自定义一个Holder 把一个Item中的View绑定起来
            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.gridview_item, null);
                holder = new Holder2(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (Holder2) view.getTag();
            }
            holder.item_image.setImageResource((Integer) Menudata_list.get(position).get("ItemImage"));

            if(selected == position) {
                // the special one.Scale Large
                holder.item_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                // the rest.Scale small
                holder.item_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }


            return view;
        }

        private int selected = -1;
        public void notifyDataSetChanged(int id) {
            selected = id;
            super.notifyDataSetChanged();
        }
    }

    public class Holder2 {
        public ImageView item_image;


        public Holder2(View view) {
            item_image = (ImageView) view.findViewById(R.id.ItemImage);





        }

    }

   /* class MyAdapter extends BaseAdapter{

        private LayoutInflater inflater;
        private Context mContext = null;

        public MyAdapter(Context context) {
            mContext = context;
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.gridview_item, null);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.iv.setImageResource(icon[position]);
            return convertView;
        }
    }
    class ViewHolder{
        ImageView iv;
        TextView tv_name;
    }

    private void initData() {
        datas = new ArrayList<String>();
        for(int i=0;i<icon.length;i++){
            datas.add("专辑列表---"+i);
        }
    }*/


}





