import java.util.Scanner;

/**
 * Created by admin on 2017/7/31.
 */
public class Menu {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    ClientManager clientManager;
    GoodsManager goodsManager;

    public Menu() {
        clientManager = new ClientManager();
        goodsManager = new GoodsManager();
    }

    // 显示系统菜单
    public void showSystemMenu() {
        System.out.println("\t\t\t欢迎使用我行我素购物管理系统1.0版\t\t\t");
        System.out.println("********************************************************\n");
        System.out.println("\t\t\t\t    1. 登录系统\n");
        System.out.println("\t\t\t\t    2. 更改管理员密码\n");
        System.out.println("\t\t\t\t    3. 退出系统\n");
        System.out.println("********************************************************");
        System.out.println("请选择，输入数字：");
        Scanner scanner = Main.getScanner();
        int menuIndex = scanner.nextInt();
        switch (menuIndex) {
            case 1:
                login(scanner);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                System.out.println("输入不正确，请重新输入");
        }
    }

    // 登录
    public void login(Scanner scanner) {
        String username, password;
        System.out.println("请输入用户名：");
        username = scanner.next();
        System.out.println("请输入密码：");
        password = scanner.next();
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // 登录成功
            System.out.println("登录成功");
            showUserMenu(scanner);
        } else {
            // 登录失败
            System.out.println("用户名或密码不正确，要重试吗？(y/n)");
            String retry = scanner.next();
            if ("y".equals(retry)) {
                // 重新登录
                login(scanner);
            } else {
                // 返回上级菜单
                showSystemMenu();
            }
        }
    }

    // 显示登录成功后的用户菜单
    public void showUserMenu(Scanner scanner) {
        System.out.println("\t\t\t欢迎使用我行我素购物管理系统1.0版\t\t\t");
        System.out.println("********************************************************\n");
        System.out.println("\t\t\t\t    1. 客户信息管理\n");
        System.out.println("\t\t\t\t    2. 购物结算\n");
        System.out.println("\t\t\t\t    3. 真情回馈\n");
        System.out.println("\t\t\t\t    4. 注销\n");
        System.out.println("********************************************************");
        System.out.println("请选择，输入数字：");
        int index = scanner.nextInt();
        switch (index) {
            case 1:
                showCustomManageMenu(scanner);
                break;
            case 2:
                showGoodsMenu(scanner);
                break;
            case 3:
                break;
            case 4:
                logout(scanner);
                break;
        }
    }

    // 显示商品菜单
    public void showGoodsMenu(Scanner scanner) {
        System.out.println("********************************************************\n");
        System.out.println("请选择购买的商品编号：");
        Goods[] goods = goodsManager.goods;
        for (int i = 0; i < goods.length; i++) {
            System.out.println("\t\t\t\t    " + (i + 1) + ". " + (goods[i].name) + "\n");
        }
        System.out.println("********************************************************");
        System.out.println("请输入会员号：");
        int clientId = scanner.nextInt();
        Client client = clientManager.searchClientById(clientId);
        if (client == null) {
            System.out.println("会员不存在！");
            showUserMenu(scanner);
        } else {
            goodsManager.startSelectGoods();
            selectGoods(client, scanner);
        }
    }

    // 选择商品
    private void selectGoods(Client client, Scanner scanner) {
        System.out.println("请输入商品编号：");
        int goodsId = scanner.nextInt();
        Goods goods = goodsManager.getGoodsByIndex(goodsId);
        if (goods == null) {
            // 选择的商品不存在，goodsId不在备选的商品中
            System.out.println("商品编号不正确！");
            selectGoods(client, scanner);
            return ;
        }
        System.out.println("请输入商品数量：");
        int goodsCount = scanner.nextInt();
        SelectedGoods selectedGoods = new SelectedGoods(goods, goodsCount);
        goodsManager.addSelectedGoods(selectedGoods);
        System.out.println("是否继续(y/n)");
        if ("y".equals(scanner.next())) {
            // 继续
            selectGoods(client, scanner);
        } else {
            // 不继续，结算
            int getPoints = goodsManager.showSelectedGoods();
            // 积分累加
            client.points += getPoints;
            System.out.println("按'n'返回上一级菜单");
            if ("n".equals(scanner.next())) {
                showUserMenu(scanner);
            }
        }
    }

    // 显示客户信息管理菜单
    public void showCustomManageMenu(Scanner scanner) {
        System.out.println("********************************************************\n");
        System.out.println("\t\t\t\t    1. 显示所有客户信息\n");
        System.out.println("\t\t\t\t    2. 添加客户信息\n");
        System.out.println("\t\t\t\t    3. 修改客户信息\n");
        System.out.println("\t\t\t\t    4. 查询客户信息\n");
        System.out.println("********************************************************");
        System.out.println("请选择，输入数字(按0返回上级菜单)：");
        int index = scanner.nextInt();
        switch (index) {
            case 0:
                showUserMenu(scanner);
                break;
            case 1:
                clientManager.showAllClients();
                showCustomManageMenu(scanner);
                break;
            case 2:
                addClient(scanner);
                break;
            case 3:
                modifyClientInfoMenu(scanner);
                break;
            case 4:
                searchClient(scanner);
                break;
        }
    }

    // 添加用户
    private void addClient(Scanner scanner) {
        int clientId;
        String birthday;
        int points;
        System.out.println("请输入会员号<4位整数>：");
        clientId = scanner.nextInt();
        System.out.println("请输入会员生日<月/日 用两位数表示>：");
        birthday = scanner.next();
        System.out.println("请输入会员积分：");
        points = scanner.nextInt();
        Client client = new Client(clientId, birthday, points);
        boolean result = clientManager.addClient(client);
        if (result) {
            // 添加客户成功
            System.out.println("添加客户信息成功，要继续添加吗？(y/n)");
            String retry = scanner.next();
            if ("y".equals(retry)) {
                addClient(scanner);
            } else {
                // 返回上级菜单
                showCustomManageMenu(scanner);
            }
        } else {
            // 添加客户失败
            System.out.println("添加客户信息失败");
            showCustomManageMenu(scanner);
        }
    }

    // 查询客户信息
    private void searchClient(Scanner scanner) {
        System.out.println("请输入会员号：");
        int clientId = scanner.nextInt();
        Client client = clientManager.searchClientById(clientId);
        if (client == null) {
            System.out.println("会员不存在！");
        } else {
            System.out.println("会员信息如下：");
            System.out.println("会员号\t\t\t生　日\t\t\t积　分");
            System.out.println(client.id + "\t\t\t" + client.birthday + "\t\t\t" + client.points);
        }
        showCustomManageMenu(scanner);
    }

    // 修改会员信息
    private void modifyClientInfoMenu(Scanner scanner) {
        System.out.println("请输入会员号：");
        int clientId = scanner.nextInt();
        Client client = clientManager.searchClientById(clientId);
        if (client == null) {
            System.out.println("会员不存在！");
            showCustomManageMenu(scanner);
        } else {
            System.out.println("********************************************************\n");
            System.out.println("\t\t\t\t    1. 修改会员生日\n");
            System.out.println("\t\t\t\t    2. 修改会员积分\n");
            System.out.println("********************************************************");
            System.out.println("请选择，输入数字：");
            int index = scanner.nextInt();
            switch (index) {
                case 1:
                    System.out.println("请输入会员生日<月/日 用两位数表示>：");
                    String newBirthday = scanner.next();
                    client.birthday = newBirthday;
                    System.out.println("修改会员生日成功！");
                    break;
                case 2:
                    System.out.println("请输入会员积分：");
                    int newPoints = scanner.nextInt();
                    client.points = newPoints;
                    System.out.println("修改会员积分成功！");
                    break;
                default:
                    System.out.println("输入有误！");
            }
            showCustomManageMenu(scanner);
        }
    }

    // 注销
    private void logout(Scanner scanner) {
        System.out.println("确定要注销吗？(y/n)");
        String result = scanner.next();
        if ("y".equals(result)) {
            showSystemMenu();
        } else {
            showUserMenu(scanner);
        }
    }
}
