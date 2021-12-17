package play.gator.farmgator.BookOrder;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;

public class ShowBookAdapter extends
        RecyclerView.Adapter<ShowBookAdapter.ExampleViewHolder> implements Filterable {
    private Context mcon;
    private List<farmer> exampleList;
    class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView Farmer,Adhar, Village,NoFarm;
        Button ViewFarm;
        ExampleViewHolder(View itemView) {
            super(itemView);
            // imageView = itemView.findViewById(R.id.image_view);
            Farmer = itemView.findViewById(R.id.Farmername);
            Adhar = itemView.findViewById(R.id.Adharno);
            Village = itemView.findViewById(R.id.village);
            NoFarm = itemView.findViewById(R.id.farmno);
            ViewFarm=itemView.findViewById(R.id.viewfarm);
        }
    }

    public ShowBookAdapter(Context mcon, List<farmer> exampleList) {
        this.mcon = mcon;
        this.exampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        farmer currentItem = exampleList.get(position);
        // holder.imageView.setImageResource(currentItem.getImageResource());
        holder.Farmer.setText(currentItem.getName());
        holder.Adhar.setText(currentItem.getAadhar());
        holder.Village.setText(currentItem.getAddress());
        holder.NoFarm.setText(String.valueOf(currentItem.getFarmdata().size()));
        holder.ViewFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcon, BookFarm.class);
                i.putExtra("farmerModel",currentItem);
                i.putExtra("farmDataList", (Serializable) currentItem.getFarmdata());
                mcon.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<farmer> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (farmer item : exampleList) {
                    if (item.getAadhar().toLowerCase().contains(filterPattern)) {
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
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
