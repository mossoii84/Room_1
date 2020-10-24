package com.example.room_1.adapterRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.room_1.R;
import com.example.room_1.data.User;
import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<MainViewHolder> implements View.OnClickListener{
   List<User> usersList = new ArrayList<>(); //стандартная запись добавления массив нашего класса (с переменными)

    @Override
    public void onClick(View view) { }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // стандартная запись
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false); // стандартная запись
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
     holder.bind(usersList.get(position));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    //Это не Стандартный метод и по этому мы создаем его сами
    // Метод для обновления списка, если у нас изменился список и мы хотим показать актуальный список
    public void updateList(List<User> usersList) {
        this.usersList = usersList;
        // Уведомляем адаптор о том, что у нас поменялись данные и необходимо заново слинковать данные и вьюшки
        // То есть, заново будет вызваться метод onBindViewHolder
        notifyDataSetChanged();

        //  notifyDataSetChanged() уведомляет прикрепленных наблюдателей, что базовые данные были изменены, и любое представление,
        //  отражающее набор данных, должно обновиться.
     //  notifyItemRemoved(position) уведомляет адаптер RecyclerView, что данные в адаптере были удалены в определенной позиции.

    }



    //создаем метод для ПЕРЕТАСКИВАНИЯ, получаем позицию item-са
    public  User getUserPosition(int position){
        return usersList.get(position);
    }

}
