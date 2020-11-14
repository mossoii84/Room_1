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

//Так как MainViewHolder сделал не как отдельный фаил а в Абаптере, то тут extends указываем
// путь RoomAdapter.MainViewHolder
public class RoomAdapter extends RecyclerView.Adapter<MainViewHolder> implements View.OnClickListener{
        List<User> users = new ArrayList<>(); //стандартная запись добавления массив нашего класса (с переменными)

        private OnItemClickListener listener;
        // Получаем доступ(подключаем) к нашему классу-интерфайсу и делаем для досутпа к нему здесь конструктор
        // конструктор не желательно делать только для переменных!!! который нужно будет потом передовать
        // в Activity из за конструктора придется передавать users roomAdapter = new RoomAdapter(users); - это плохо!!!
        // listenera(от OnItemClickListener ) можно
        public RoomAdapter(OnItemClickListener listener){
            this.listener = listener;
        }


        //это имплиминтировали метод из implements View.OnClickListener
    @Override
    public void onClick(View view) { }

        @NonNull
        @Override // он создает то кол=во эл которое необходимо отобразить на экране 10 например
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // стандартная запись
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false); // стандартная запись
            return new MainViewHolder(view);
        }

        @Override // подготавливает ViewHolder для отображения на экране, он обновляет при шкроле СТАРЫЙ на НОВЫЙ или при добавлении,
        // при этом не создавая новые
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            //set position
            holder.bind(users.get(position), listener);
        }

        @Override
        public int getItemCount() { return users.size(); }


        //Это не Стандартный метод и по этому мы создаем его сами
        // Метод для обновления списка, если у нас изменился список и мы хотим показать актуальный список
        public void updateList(List<User> users) {
            this.users = users;
            // Уведомляем адаптор о том, что у нас поменялись данные и необходимо заново слинковать данные и вьюшки
            // То есть, заново будет вызваться метод onBindViewHolder
            notifyDataSetChanged();

            //  notifyDataSetChanged() уведомляет прикрепленных наблюдателей, что базовые данные были изменены, и любое представление,
            //  отражающее набор данных, должно обновиться при это важно понимать что обновляются что были(items) а не создаются новые.
            //  notifyItemRemoved(position) уведомляет адаптер RecyclerView, что данные в адаптере были удалены в определенной позиции.

        }
        //создаем метод для ПЕРЕТАСКИВАНИЯ, получаем позицию item-са
        public User getUserPosition(int position){
            return users.get(position);
        }


}




