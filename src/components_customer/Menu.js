import React from 'react';
import './customerWindowGUI.css'
import MenuItems from './MenuItem';


function Menu(props) {
    const{items, onAdd} = props;
    return (
        <main className = "width-2">
            <h2 className="sub-headers1">Menu Items</h2>
            <div className="row"></div>
            {items?.map((item) => (
                <MenuItems key={item?.id} item={item} onAdd={onAdd}></MenuItems>
            ))}
        </main>
    )
}

export default Menu;