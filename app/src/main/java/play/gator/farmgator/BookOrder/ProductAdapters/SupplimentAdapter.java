package play.gator.farmgator.BookOrder.ProductAdapters;



import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.BookOrder.ProductModel;
import play.gator.farmgator.R;


public class SupplimentAdapter extends RecyclerView.Adapter<SupplimentAdapter.FruitHolder>{

    Context context;
    List<ProductModel> list = new ArrayList<>();
    public int number=0,total=0;
    String quantity,finall;



    public SupplimentAdapter(Context context, List<ProductModel> list,String quantity,String finall) {
        this.context = context;
        this.list = list;
        this.quantity=quantity;
        this.finall=finall;
    }


    @Override
    public FruitHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.itemsuppliment,parent,false);


        return new FruitHolder(view);
    }

    @Override
    public void onBindViewHolder(final FruitHolder holder, final int position) {

        ProductModel productModel = list.get(position);

        // holder.title.setText("Fertlizer Products");
        holder.supplimentname.setText(productModel.getName());
        holder.supplimentprice.setText(productModel.getPrice());

        // holder.tv_price.setText(productModel.getPrice());



        holder.checkBox.setChecked(productModel.isSelected());
        holder.checkBox.setTag(list.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductModel productModel = (ProductModel) holder.checkBox.getTag();

                productModel.setSelected(holder.checkBox.isChecked());

                list.get(position).setSelected(holder.checkBox.isChecked());
                holder.numberedit.setClickable(true);



            }
        });
        holder.numberedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        // implement the TextWatcher callback listener
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // get the content of both the edit text



            }

            @Override
            public void afterTextChanged(Editable s) {

                quantity = s.toString();
                if (quantity.matches("")) {
                    quantity="0";
                }

                number= Integer.parseInt(quantity);
                total= number*Integer.parseInt( holder.supplimentprice.getText().toString());
                finall= String.valueOf(total);
                Toast.makeText(context, "Total is"+ total, Toast.LENGTH_SHORT).show();
                productModel.setNumber(String.valueOf(number));
                productModel.setTotal(total);
                holder.total.setText(finall);

            }
        };
        holder.numberedit.addTextChangedListener(textWatcher);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class FruitHolder extends RecyclerView.ViewHolder{

        TextView supplimentname,supplimentprice,total,title;
        CheckBox checkBox;
        EditText numberedit;

        public FruitHolder(View itemView) {
            super(itemView);

            supplimentname = itemView.findViewById(R.id.supplimentname);
            total = itemView.findViewById(R.id.supplimenttotal);
            numberedit = itemView.findViewById(R.id.supplimentnumber);
            supplimentprice = itemView.findViewById(R.id.supplimentprice);
            checkBox = itemView.findViewById(R.id.checkBox_select);
            //title = itemView.findViewById(R.id.toolbar_review1);



        }
    }

    public List<ProductModel> getFruitsList5(){
        return list;
    }


}
