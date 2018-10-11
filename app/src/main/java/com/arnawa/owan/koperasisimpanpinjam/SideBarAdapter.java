package com.arnawa.owan.koperasisimpanpinjam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SideBarAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> listParent;
    private HashMap<String, List<String>> listChild;

    public SideBarAdapter(Context context, List<String> listParent, HashMap<String, List<String>> listChild) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listParent = listParent;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return this.listParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 1; // if group don't have child, return 0

        if (groupPosition == Constant.S_SIMPANPINJAM_PINJAMAN) {
            childCount = this.listChild.get(this.listParent.get(groupPosition)).size();
        }
        if (groupPosition == Constant.S_SIMPANPINJAM_SIMPANAN){
            childCount = this.listChild.get(this.listParent.get(groupPosition)).size();
        }
        if (groupPosition == Constant.S_SIMPANPINJAM_LAPORAN) {
            childCount = this.listChild.get(this.listParent.get(groupPosition)).size();
        }
        return childCount;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChild.get(this.listParent.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder holder;
        String parentTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sidebar_parent, null);
            holder = new ParentHolder();
            holder.parentIcon  = (ImageView) convertView.findViewById(R.id.icon_img);
            holder.parentTitle = (TextView)  convertView.findViewById(R.id.parent_text);
            holder.parentImage = (ImageView) convertView.findViewById(R.id.parent_img);
            convertView.setTag(holder);
        } else {
            holder = (ParentHolder) convertView.getTag();
        }

        holder.parentTitle.setText(parentTitle);

        if (groupPosition == Constant.S_SIMPANPINJAM_DASHBOARD){
            holder.parentIcon.setImageResource(R.drawable.pos_beranda);
        }

        if (groupPosition == Constant.S_SIMPANPINJAM_PINJAMAN){
            holder.parentIcon.setImageResource(R.drawable.pos_pinjam);
        }

        if (groupPosition == Constant.S_SIMPANPINJAM_SIMPANAN){
            holder.parentIcon.setImageResource(R.drawable.pos_simpan);
        }

        if (groupPosition == Constant.S_SIMPANPINJAM_LAPORAN){
            holder.parentIcon.setImageResource(R.drawable.pos_laporan);
        }




        if (getChildrenCount(groupPosition) != 0) { // if group have child
            if (isExpanded)
                holder.parentImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_arrow_down_black));
            else
                holder.parentImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_arrow_right_black));
        } else { // if group don't have child
            holder.parentImage.setVisibility(View.GONE); // remove arrow icon
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;
        String childTitle = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_sidebar_child, null);
            holder = new ChildHolder();
            holder.childTitle = (TextView) convertView.findViewById(R.id.child_text);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.childTitle.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ParentHolder {
        private TextView  parentTitle;
        private ImageView parentImage;
        private ImageView parentIcon;
    }

    static class ChildHolder {
        private TextView childTitle;
    }

}
