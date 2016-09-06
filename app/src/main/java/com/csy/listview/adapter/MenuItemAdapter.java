package com.csy.listview.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csy.navigationview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chensy160806 on 2016/9/6.
 */
public class MenuItemAdapter extends BaseAdapter
{
    private final int mIconSize;
    private LayoutInflater mInflater;
    private Context mContext;

    public MenuItemAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
        mContext = context;

        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);//24dp
    }

    private List<LvMenuItem> mItems = new ArrayList<LvMenuItem>(
            Arrays.asList(
                    new LvMenuItem(R.drawable.ic_aq, "Home"),
                    new LvMenuItem(R.drawable.ic_humidity, "Messages"),
                    new LvMenuItem(R.drawable.ic_cold, "Friends"),
                    new LvMenuItem(R.drawable.ic_exercise, "Discussion"),
                    new LvMenuItem(),
                    new LvMenuItem("Sub Items"),
                    new LvMenuItem(R.drawable.ic_github, "Sub Item 1"),
                    new LvMenuItem(R.drawable.ic_settings, "Sub Item 2"),
                    new LvMenuItem(R.drawable.ic_introduce, "Sub Item 3")
            ));


    @Override
    public int getCount()
    {
        return mItems.size();
    }


    @Override
    public Object getItem(int position)
    {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mItems.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LvMenuItem item = mItems.get(position);
        switch (item.type)
        {
            case LvMenuItem.TYPE_NORMAL:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_lvitem, parent,
                            false);
                }
                TextView itemView = (TextView) convertView;
                itemView.setText(item.name);
                //Drawable icon = mContext.getResources().getDrawable(item.icon);//Android SDK 升級到 23 之後，getResource.getColor(R.color.color_name) 過時
                Drawable icon = ContextCompat.getDrawable(mContext,item.icon);
                setIconColor(icon);
                if (icon != null)
                {
                    icon.setBounds(0, 0, mIconSize, mIconSize);
                    TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, null, null);
                }

                break;
            case LvMenuItem.TYPE_NO_ICON:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_lvitem_subheader, //带小标题的
                            parent, false);
                }
                TextView subHeader = (TextView) convertView;
                subHeader.setText(item.name);
                break;
            case LvMenuItem.TYPE_SEPARATOR:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_lvitem_separator, //单独的
                            parent, false);
                }
                break;
        }

        return convertView;
    }

    public void setIconColor(Drawable icon)
    {
        int textColorSecondary = android.R.attr.textColorSecondary;
        TypedValue value = new TypedValue();
        if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true))
        {
            return;
        }
        //int baseColor = mContext.getResources().getColor(value.resourceId);//Android SDK 升級到 23 之後，getResource.getColor(R.color.color_name) 過時
        int baseColor = ContextCompat.getColor(mContext,value.resourceId);
        icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
    }
}