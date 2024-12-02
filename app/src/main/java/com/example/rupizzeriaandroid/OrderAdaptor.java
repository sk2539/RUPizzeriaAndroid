package com.example.rupizzeriaandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class OrderAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Order> orders;

    private int selectedPosition = -1;

    public OrderAdaptor(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders != null ? orders : new ArrayList<>();
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getOrderNum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder();
            holder.orderNumberText = convertView.findViewById(R.id.orderNumberText);
            holder.pizzaCountText = convertView.findViewById(R.id.pizzaCountText);
            holder.totalAmountText = convertView.findViewById(R.id.totalAmountText);
            holder.orderDetailsText = convertView.findViewById(R.id.orderDetailsText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = orders.get(position);
        holder.orderNumberText.setText("Order #: " + order.getOrderNum());
        holder.pizzaCountText.setText("# of Pizzas: " + order.getOrder().size());
        holder.totalAmountText.setText("Total ($): " + String.format("%.2f", order.calculatePrice()));

        String details = order.getOrder().stream()
                .map(pizza -> pizza.getClass().getSimpleName() + " - " + pizza.toString())
                .reduce((a, b) -> a + ", " + b)
                .orElse("No pizzas");
        holder.orderDetailsText.setText("Order Details: " + details);

        // Highlight or reset background based on selection
        if (position == selectedPosition) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.selected_color));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
        }

        return convertView;
    }
    public void toggleSelection(int position) {
        if (selectedPosition == position) {
            selectedPosition = -1;
        } else {
            selectedPosition = position;
        }
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView orderNumberText;
        TextView pizzaCountText;
        TextView totalAmountText;
        TextView orderDetailsText;
    }

}
