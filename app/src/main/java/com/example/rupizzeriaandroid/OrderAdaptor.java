package com.example.rupizzeriaandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter class for managing and displaying a list of orders in a ListView.
 * Provides functionality for handling order selection and removal.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class OrderAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Order> orders;

    private int selectedPosition = -1;

    /**
     * Constructor for the OrderAdaptor.
     *
     * @param context The application context.
     * @param orders  The list of orders to display. If null, an empty list is initialized.
     */
    public OrderAdaptor(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders != null ? orders : new ArrayList<>();
    }

    /**
     * Removes an order from the list and updates the ListView.
     *
     * @param order The order to remove.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        notifyDataSetChanged();
    }

    /**
     * @return The number of orders in the list.
     */
    @Override
    public int getCount() {
        return orders.size();
    }

    /**
     * Retrieves the order at the specified position.
     *
     * @param position The position of the order in the list.
     * @return The order object at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    /**
     * Retrieves the unique ID of the order at the specified position.
     *
     * @param position The position of the order in the list.
     * @return The unique order number (ID) of the order.
     */
    @Override
    public long getItemId(int position) {
        return orders.get(position).getOrderNum();
    }

    /**
     * Creates or updates a view for an order at the specified position.
     *
     * @param position    The position of the order in the list.
     * @param convertView The recycled view for reuse (if available).
     * @param parent      The parent view group.
     * @return The view representing the order.
     */
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
        if (position == selectedPosition) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.selected_color));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
        }
        return convertView;
    }

    /**
     * @return The currently selected position in the list.
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Toggles the selection of an item in the list.
     * If the selected item is clicked again, the selection is cleared.
     *
     * @param position The position of the item to toggle selection for.
     */
    public void toggleSelection(int position) {
        if (selectedPosition == position) {
            selectedPosition = -1;
        } else {
            selectedPosition = position;
        }
        notifyDataSetChanged();
    }

    /**
     * Sets the currently selected position and updates the ListView.
     *
     * @param position The position to set as selected.
     */
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for efficient recycling of views in the ListView.
     */
    static class ViewHolder {
        TextView orderNumberText;
        TextView pizzaCountText;
        TextView totalAmountText;
        TextView orderDetailsText;
    }

}
