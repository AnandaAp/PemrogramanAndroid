package com.android.helloworld;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;
import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class FirebaseRecycleView extends RecyclerView.Adapter<FirebaseRecycleView.MyViewHolder> {
    String[] namaMahasiswa;
    String[] nomorMahasiswa;
    String[] phoneMahasiswa;
    DataMahasiswa dataMahasiswa = new DataMahasiswa();
    Context context;

    public FirebaseRecycleView(Context ct,String[] namaMahasiswa, String[] nomorMahasiswa, String[] phoneMahasiswa) {
        this.context = ct;
        this.namaMahasiswa = namaMahasiswa;
        this.nomorMahasiswa = nomorMahasiswa;
        this.phoneMahasiswa = phoneMahasiswa;
//        Log.i(TAG, "FirebaseRecycleView: "+namaMahasiswa[0]+" "+ nomorMahasiswa[0]+ " "+ phoneMahasiswa[0]);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mahasiswa_row,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namaMahasiswa.setText(namaMahasiswa[position]);
        holder.nomorMahasiswa.setText(nomorMahasiswa[position]);
        holder.phone.setText(phoneMahasiswa[position]);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                moveToTambahDataMahasiswa(v);
                Dashboard.getViewPager().setCurrentItem(3);
                Log.i(TAG, "onClick: yes");
                DataMahasiswa.getNamaMahasiswa().setText(namaMahasiswa[position]);
                DataMahasiswa.getNoMahasiswa().setText(nomorMahasiswa[position]);
                DataMahasiswa.getPhone().setText(phoneMahasiswa[position]);
                DataMahasiswa.getBtnSave().setText("UPDATE");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.namaMahasiswa.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.nomorMahasiswa.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.phone.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    public int getItemCount() {
        return namaMahasiswa.length;
    }
    private void moveToTambahDataMahasiswa(View view){
//        Navigation.findNavController(view).navigate(R.id.dataMahasiswa2);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView namaMahasiswa,nomorMahasiswa,phone;
        CardView cv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            namaMahasiswa = itemView.findViewById(R.id.namaRv);
            nomorMahasiswa = itemView.findViewById(R.id.nomorMahasiswaRv);
            phone = itemView.findViewById(R.id.phoneLabelRv);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.i(TAG, "onClick: "+pos);
        }
    }



}
