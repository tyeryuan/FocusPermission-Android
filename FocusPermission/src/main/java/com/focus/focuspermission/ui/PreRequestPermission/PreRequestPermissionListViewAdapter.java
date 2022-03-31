package com.focus.focuspermission.ui.PreRequestPermission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.focus.focuspermission.R;
import com.focus.focuspermission.core.PermissionInfo;

import java.util.List;

public class PreRequestPermissionListViewAdapter extends BaseAdapter {

    private List<PreRequestPermissionItemInfo> preRequestPermissionItemInfos;
    private LayoutInflater inflater;
    private Context context;

    public PreRequestPermissionListViewAdapter(Context context, List<PreRequestPermissionItemInfo> preRequestPermissionItemInfos){
        this.context = context;
        this.preRequestPermissionItemInfos = preRequestPermissionItemInfos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (preRequestPermissionItemInfos == null){
            return 0;
        }
        return preRequestPermissionItemInfos.size();
    }

    @Override
    public Object getItem(int position) {
        if (preRequestPermissionItemInfos == null){
            return null;
        }
        return preRequestPermissionItemInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PreRequestPermissionItemViewInfo preRequestPermissionItemViewInfo;
        PreRequestPermissionItemInfo itemInfo = (PreRequestPermissionItemInfo) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.focus_permission_prerequest_item, null);
            preRequestPermissionItemViewInfo = new PreRequestPermissionItemViewInfo(position);
            preRequestPermissionItemViewInfo.setPreRequestPermissionItemInfo(itemInfo);
            preRequestPermissionItemViewInfo.setTextView_permission(convertView.findViewById(R.id.textView_PermissionName));
            preRequestPermissionItemViewInfo.setTextView_description(convertView.findViewById(R.id.textView_PermissionDescription));
            preRequestPermissionItemViewInfo.setBtn_grant(convertView.findViewById(R.id.button_grant));
            convertView.setTag(preRequestPermissionItemViewInfo);
            convertView.setMinimumHeight(70);
        } else {
            preRequestPermissionItemViewInfo = (PreRequestPermissionItemViewInfo) convertView.getTag();
        }

        preRequestPermissionItemViewInfo.updateGrantStateUI();

        String permissionName = itemInfo.getPermission();
        String description = itemInfo.getPermission();
        if (itemInfo.getPermissionInfo()!=null) {
            permissionName = itemInfo.getPermissionInfo().getName();
            description = itemInfo.getPermissionInfo().getDescription();
        }
        preRequestPermissionItemViewInfo.getTextView_permission().setText(permissionName);
        preRequestPermissionItemViewInfo.getTextView_description().setText(description);
        return convertView;
    }

}
