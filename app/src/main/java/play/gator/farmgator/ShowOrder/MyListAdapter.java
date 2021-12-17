package play.gator.farmgator.ShowOrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<MyListData> listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(List<MyListData> listdata) {
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemshoworder, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.index.setText("Order "+String.valueOf(position+1));
        holder.farmername.setText(listdata.get(position).getFarmername());
        holder.farmno.setText(listdata.get(position).getFarmno());
        holder.billamount.setText(listdata.get(position).getBillamount());
        holder.itemname.setText(listdata.get(position).getItemname().replaceFirst(", ",""));
        holder.orderdate.setText(listdata.get(position).getOrderdate());
        holder.orderstatus.setText(listdata.get(position).getOrderstatus());
        holder.longitude.setText(myListData.getLongitude());
        holder.latitude.setText(myListData.getLatitude());
        holder.farmerAddress.setText(myListData.getFarmerAddress());
        holder.orderId.setText(myListData.getOrderID());



    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MyListData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listdata);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MyListData item : listdata) {
                    if (item.getFarmername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listdata.clear();
            listdata.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView farmername,farmno,billamount,itemname,orderdate,orderstatus,index;
        TextView farmerAddress, latitude, longitude, orderId;

        public ViewHolder(View itemView) {
            super(itemView);

            this.billamount = (TextView) itemView.findViewById(R.id.billamount);
            this.farmerAddress = (TextView) itemView.findViewById(R.id.farmeraddress);
            this.latitude = (TextView) itemView.findViewById(R.id.lattitude);
            this.longitude = (TextView) itemView.findViewById(R.id.longitude);
            this.orderId = (TextView) itemView.findViewById(R.id.orderId);
            this.farmername = (TextView) itemView.findViewById(R.id.farmername);
            this.farmno = (TextView) itemView.findViewById(R.id.farmno);
            this.itemname = (TextView) itemView.findViewById(R.id.itemcount);
            this.orderdate = (TextView) itemView.findViewById(R.id.orderdate);
            this.orderstatus = (TextView) itemView.findViewById(R.id.orderstatus);
            this.index = (TextView) itemView.findViewById(R.id.index);

        }
    }

}
