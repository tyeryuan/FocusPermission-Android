package com.focus.focuspermission.ui.PreRequestPermission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.focus.focuspermission.R;

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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.focus_permission_prerequest_item, null);
            preRequestPermissionItemViewInfo = new PreRequestPermissionItemViewInfo(position);
            preRequestPermissionItemViewInfo.setPreRequestPermissionItemInfo(preRequestPermissionItemInfos.get(position));
            preRequestPermissionItemViewInfo.setTextView_permission(convertView.findViewById(R.id.textView_PermissionName));
            preRequestPermissionItemViewInfo.setTextView_description(convertView.findViewById(R.id.textView_PermissionDescription));
            preRequestPermissionItemViewInfo.setBtn_grant(convertView.findViewById(R.id.button_grant));
            convertView.setTag(preRequestPermissionItemViewInfo);
        } else {
            preRequestPermissionItemViewInfo = (PreRequestPermissionItemViewInfo) convertView.getTag();
        }

        preRequestPermissionItemViewInfo.getTextView_permission().setText(preRequestPermissionItemInfos.get(position).getPermission());

        return convertView;
    }
}
