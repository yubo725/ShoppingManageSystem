/**
 * Created by admin on 2017/7/31.
 * 客户管理类
 */
public class ClientManager {

    private static final int CLIENT_MAX_SIZE = 50;

    public static Client[] clients = new Client[CLIENT_MAX_SIZE];
    public static int size = 0;

    public ClientManager() {

    }

    // 添加客户信息
    public boolean addClient(Client client) {
        if (size >= CLIENT_MAX_SIZE) {
            System.out.println("客户信息已满，无法添加新客户!");
            return false;
        } else {
            clients[size++] = client;
            System.out.println("添加客户成功");
            return true;
        }
    }

    // 显示所有客户信息
    public void showAllClients() {
        System.out.println("所有客户信息如下：");
        System.out.println("会员号\t\t\t生　日\t\t\t积　分");
        for (Client client : clients) {
            if (client != null) {
                System.out.println(client.id + "\t\t\t" + client.birthday + "\t\t\t" + client.points);
            }
        }
    }

    // 根据会员号查找会员信息
    public Client searchClientById(int clientId) {
        for (Client client : clients) {
            if (client != null && client.id == clientId) {
                return client;
            }
        }
        return null;
    }

}
