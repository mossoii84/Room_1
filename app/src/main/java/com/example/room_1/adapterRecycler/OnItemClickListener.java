package com.example.room_1.adapterRecycler;


import com.example.room_1.data.User;

//создали чтобы сделать метод Клик по нажатию для Edit или Update в дальнейшем
public interface OnItemClickListener {
    void onItemClick(User user);
}
