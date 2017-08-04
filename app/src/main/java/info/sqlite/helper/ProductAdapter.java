package info.sqlite.helper;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cynthiakhalil.bmbtest.R;
import com.example.cynthiakhalil.bmbtest.displayContacts;
import com.example.cynthiakhalil.bmbtest.itemInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import info.sqlite.helper.Employee;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.blue;
import static com.example.cynthiakhalil.bmbtest.R.color.colorAccent;
import static java.security.AccessController.getContext;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Employee> eList;
    Context mContext;
    DatabaseHelper db;


    public ProductAdapter(Context mContext, List<Employee> eList) {

        this.eList = eList;
        this.mContext = mContext;
        db = new DatabaseHelper(mContext);
        Log.v("list", eList.toString());
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Employee emp = eList.get(position);
        //holder.eName.setText(emp.getName());
        holder.eName.setText(emp.getName());
        holder.ePhonenumber.setText(emp.getPhoneNumber());
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+961"+eList.get(position).getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });
        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(emp);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View view) {
                                                    /*AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                                                    LinearLayout layout = new LinearLayout(mContext);
                                                    layout.setOrientation(LinearLayout.VERTICAL);
                                                    final Button yes = new Button(mContext);
                                                    final Button no = new Button(mContext);
                                                    yes.setText("Yes");
                                                    no.setText("no");*/


                                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                                    builder.setMessage("Are you sure you want to delete?")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    db.deleteEmployee(eList.get(position).getId());
                                                                    eList.remove(position);
                                                                    notifyItemRemoved(position);
                                                                    notifyItemRangeChanged(position,eList.size());
                                                                }
                                                            })
                                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();

                                                }
                                            }


        );
        holder.btnEdit.setOnClickListener(new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View view) {
                                                    LinearLayout layout = new LinearLayout(mContext);
                                                    layout.setOrientation(LinearLayout.VERTICAL);

                                                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

                                                    final EditText lname = new EditText(mContext);
                                                    final EditText phonenumber = new EditText(mContext);
                                                    final Button btn = new Button(mContext);

                                                    lname.setText(eList.get(position).getName());
                                                    lname.setSelectAllOnFocus(true);
                                                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                                                    phonenumber.setText(eList.get(position).getPhoneNumber());

                                                    alertDialog.setTitle("Edit Item:");

                                                    layout.addView(lname);
                                                    layout.addView(phonenumber);

                                                    alertDialog.setView(layout);

                                                    alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            Employee emp = eList.get(position);
                                                            emp.setName(lname.getText().toString());
                                                            emp.setPhoneNumber(phonenumber.getText().toString());
                                                            eList.remove(position);
                                                            eList.add(position, emp);
                                                            db.updateEmployee(eList.get(position));



                                                            Log.e("dialogue:", eList.get(position).getPhoneNumber());
                                                            notifyDataSetChanged();
                                                        }
                                                    });

                                                    alertDialog.show();
                                                }
                                            }


        );




        if(emp.getType().equals("Manager"))
        {
            holder.type.setBackgroundColor(Color.parseColor("#D24626"));
        }
        else if(emp.getType().equals("Supervisor"))
        {
            holder.type.setBackgroundColor(Color.parseColor("#76BB1E"));
        }
        else
        {
            holder.type.setBackgroundColor(Color.parseColor("#1478A7"));
        }

        holder.seListners(position);
    }
    @Override
    public int getItemCount() {
        Log.v("listsize adapter", String.valueOf(eList.size()));
        return eList.size();
    }


    private void openDetailActivity(Employee emp)
    {
        Intent i = new Intent(mContext, itemInfo.class);

        i.putExtra("E_ID", emp.getId());
        i.putExtra("E_NAME", emp.getName());
        i.putExtra("E_PN", emp.getPhoneNumber());
        i.putExtra("E_ADD", emp.getAddress());
        i.putExtra("E_TYPE", emp.getType());
        i.putExtra("E_ON", emp.getOrgName());
        i.putExtra("E_BRANCH", emp.getBranchName());
        i.putExtra("E_NOE", emp.geteNbr());
        i.putExtra("E_PERM", emp.getPermissions());

        mContext.startActivity(i);
    }








    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView eName, ePhonenumber;
        RelativeLayout empItem;
        public ImageView type;
        int position;
        ImageView btnCall;
        itemClickListener itemClickListener;
        ImageView btnDelete;
        ImageView btnEdit;





        public void setItemClickListener(itemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        
        public ProductViewHolder(View itemView) {
            super(itemView);
            empItem = itemView.findViewById(R.id.empItem);
            eName = (TextView) itemView.findViewById(R.id.eName);
            ePhonenumber = (TextView) itemView.findViewById(R.id.ePhonenumber);
            type = (ImageView) itemView.findViewById(R.id.imageView);
            btnCall = (ImageView) itemView.findViewById(R.id.call);
            btnDelete = (ImageView) itemView.findViewById(R.id.deleteBtn);
            btnEdit = (ImageView) itemView.findViewById(R.id.editBtn);
        }



        @Override
        public void onClick(View view) {
            Employee emp = eList.get(position);
            String eName = emp.getName();
            Toast.makeText(view.getContext(), eName, Toast.LENGTH_LONG).show();
            this.itemClickListener.onItemClick(this.getLayoutPosition());


        }



        public void seListners(int position) {
            this.position = position;
            empItem.setOnClickListener(ProductViewHolder.this);
        }


    }


    public void setFilter(List<Employee> newList)
    {
        eList = new ArrayList<>();
        eList.addAll(newList);
        notifyDataSetChanged();
    }
}