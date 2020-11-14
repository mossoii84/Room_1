package com.example.room_1.adapterRecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_1.R;
import com.example.room_1.data.User;


public class MainViewHolder extends RecyclerView.ViewHolder {
    //Initialize
    public TextView textViewfirstName;
    public TextView textViewlastName;


    //в этом методе мы как бы подключаем доступ к нашему Card (т.е RecyclerView) через Адаптер
    // itemView - строка(колонка) нашего View, те в нашем случаи одна ячейка Card на экарне,
    // при прокрутке экрана только эти значения пересоздается внутри 1 Card
    public MainViewHolder(@NonNull final View itemView) {
        super(itemView);
        textViewfirstName=itemView.findViewById(R.id.textViewfirstName);
        textViewlastName=itemView.findViewById(R.id.textViewlastName);
    }

    // метод bind создается автоматически при привязке
    // holder.bind(moneyList.get(position)); в классе Adapter
    // bind это метод и лучше в нем писать новые действия так как он срабатывает не каждый раз
    // при создании адаптером MainViewHolder при прокрутке экрана
    public void bind(final User user, final OnItemClickListener listener) {
        textViewfirstName.setText(user.firstName);
        textViewlastName.setText(user.lastName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(user);

            }
        });
    }
}
