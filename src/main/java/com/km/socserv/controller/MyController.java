package com.km.socserv.controller;


import com.km.socserv.entity.Accommodation;
import com.km.socserv.entity.Executor;
import com.km.socserv.entity.Order;
import com.km.socserv.entity.User;
import com.km.socserv.service.AccommodationService;
import com.km.socserv.service.ExecutorService;
import com.km.socserv.service.OrderService;
import com.km.socserv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private ExecutorService executorService;

    @GetMapping("/check_role")
    public String checkRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            User user = (User)authentication.getPrincipal();

            switch (user.getRole()){
                case "ROLE_ADMIN" :
                    return "redirect:/admin";
                case "ROLE_USER" :
                    return  "redirect:/user";
                case "ROLE_OPERATOR" :
                    return "redirect:/operator";
            }
        }

        return "redirect:/";
    }

    @GetMapping("/admin")
    public String showAdminUI(){
        return "admin";
    }

    @GetMapping("/operator")
    public String showOperatorUI(Model model){
        List<Order> activeOrders = orderService.findAllActiveOrders();
        model.addAttribute("allActiveOrders", activeOrders);
        model.addAttribute("statusEnum", Order.StatusEnum.values());
        model.addAttribute("changedOrder", new Order());
        List<Executor> executors = executorService.findAll();
        model.addAttribute("allExecutors", executors);
        List<Order> inactiveOrders = orderService.findAllInactiveOrders();
        model.addAttribute("allInactiveOrders", inactiveOrders);

        return "operator";
    }

    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute("changedOrder") Order userOrder, Model model){
        if (userOrder.getExecutorId() != 0)
            userOrder.setExecutor(executorService.findById(userOrder.getExecutorId()));
        orderService.updateOrderStatusAndExecutor(userOrder);
        return "redirect:/operator";
    }

    @GetMapping("/new")
    public String showRegistrationForm(Model model){


        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/new")
    public String createCustomer(@ModelAttribute("newUser") User newUser){
        newUser.setRole("ROLE_USER");
        newUser.setPassword("{noop}"+newUser.getPassword());
        userService.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String showUserUI(Model model){
            User user = userService.getCurrentUser();

            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("order", new Order(Order.StatusEnum.ОТПРАВЛЕНО));

                List<Order> orders = orderService.findAllByUser(user);
                model.addAttribute("allUserOrders", orders);

                List<Accommodation> accommodations = accommodationService.findAll();
                model.addAttribute("allAccommodations", accommodations);
                return "user";
            }

            return "redirect:/";
    }

    @PostMapping("/orders")
    public String createOrder(@ModelAttribute("order") Order order){
        order.setStatus(Order.StatusEnum.ОТПРАВЛЕНО);
        order.setAccommodation(accommodationService.findById(order.getAccommodationId()));
        orderService.save(order);
        return "redirect:/user";
    }

    @GetMapping("/all_users")
    public String reportAllUsers(Model model){
        List<User> users = userService.findAllByRole("ROLE_USER");
        model.addAttribute("allUsers", users);
        return "all_users";
    }

    @GetMapping("/all_accommodations")
    public String reportAllAccommodations(Model model){
        List<Accommodation> accommodations = accommodationService.findAll();
        model.addAttribute("allAccommodations", accommodations);
        return "all_accommodations";
    }

    @GetMapping("/all_executors")
    public String reportAllExecutors(Model model){
        List<Executor> executors = executorService.findAll();
        model.addAttribute("allExecutors", executors);
        return "all_executors";
    }

    @GetMapping("/all_orders")
    public String reportAllOrders(Model model){
        List<Order> orders = orderService.findAll();
        model.addAttribute("allOrders", orders);
        return "all_orders";
    }
}
