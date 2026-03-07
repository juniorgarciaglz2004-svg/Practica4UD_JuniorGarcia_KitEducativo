package kitEducativo.gui;

import kitEducativo.datos.Empresa;
import kitEducativo.datos.Kit_Educativo;
import kitEducativo.datos.Producto;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private MongoClient cliente;
    private MongoCollection<Document> productos;
    private MongoCollection<Document> empresas;
    private MongoCollection<Document> kits;

    public void conectar() {
        cliente = new MongoClient();
        String DATABASE = "KitEducativos";
        MongoDatabase db = cliente.getDatabase(DATABASE);

        String COLECCION_PRODUCTOS = "Productos";
        productos = db.getCollection(COLECCION_PRODUCTOS);
        String COLECCION_EMPLEADOS = "Empresas";
        empresas = db.getCollection(COLECCION_EMPLEADOS);
        String COLECCION_DEPARTAMENTOS = "Kits";
        kits = db.getCollection(COLECCION_DEPARTAMENTOS);
    }

    public void desconectar() {
        cliente.close();
        cliente = null;
    }

    public MongoClient getCliente() {
        return cliente;
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        for (Document document : productos.find()) {
            lista.add(documentToProducto(document));
        }
        return lista;
    }

    public ArrayList<Producto> getProductos(String comparador) {
        ArrayList<Producto> lista = new ArrayList<>();
        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();

        listaCriterios.add(new Document("nombre", new Document("$regex", "/*" + comparador + "/*")));
        query.append("$or", listaCriterios);

        for (Document document : productos.find(query)) {
            lista.add(documentToProducto(document));
        }

        return lista;
    }

    public ArrayList<Empresa> getEmpleados() {
        ArrayList<Empresa> lista = new ArrayList<>();

        for (Document document : empleados.find()) {
            lista.add(documentToEmpleado(document));
        }
        return lista;
    }

    public ArrayList<Empresa> getEmpleados(String comparador) {
        ArrayList<Empresa> lista = new ArrayList<>();
        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();

        listaCriterios.add(new Document("nombre", new Document("$regex", "/*" + comparador + "/*")));
        listaCriterios.add(new Document("apellidos", new Document("$regex", "/*" + comparador + "/*")));
        query.append("$or", listaCriterios);

        for (Document document : empleados.find(query)) {
            lista.add(documentToEmpleado(document));
        }

        return lista;
    }

    public ArrayList<Kit_Educativo> getDepartamentos() {
        ArrayList<Kit_Educativo> lista = new ArrayList<>();

        for (Document document : departamentos.find()) {
            lista.add(documentToDepartamento(document));
        }
        return lista;
    }

    public ArrayList<Kit_Educativo> getDepartamentos(String comparador) {
        ArrayList<Kit_Educativo> lista = new ArrayList<>();
        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();

        listaCriterios.add(new Document("departamento", new Document("$regex", "/*" + comparador + "/*")));
        query.append("$or", listaCriterios);

        for (Document document : departamentos.find(query)) {
            lista.add(documentToDepartamento(document));
        }

        return lista;
    }

    public void guardarObjeto(Object obj) {
        if (obj instanceof Producto) {
            productos.insertOne(objectToDocument(obj));
        } else if (obj instanceof Empresa) {
            empleados.insertOne(objectToDocument(obj));
        } else if (obj instanceof Kit_Educativo) {
            departamentos.insertOne(objectToDocument(obj));
        }
    }

    public void modificarObjeto(Object obj) {
        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;
            productos.replaceOne(new Document("_id", producto.getId()), objectToDocument(producto));
        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;
            empleados.replaceOne(new Document("_id", empresa.getId()), objectToDocument(empresa));
        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;
            departamentos.replaceOne(new Document("_id", kitEducativo.getId()), objectToDocument(kitEducativo));
        }
    }

    public void eliminarObjeto(Object obj) {
        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;
            productos.deleteOne(objectToDocument(producto));
        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;
            empleados.deleteOne(objectToDocument(empresa));
        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;
            departamentos.deleteOne(objectToDocument(kitEducativo));
        }
    }

    public Producto documentToProducto(Document dc) {
        Producto producto = new Producto();

        producto.setId(dc.getObjectId("_id"));
        producto.setNombre(dc.getString("nombre"));
        producto.setGrados(dc.getInteger("grados"));
        producto.setPrecio((Float.parseFloat(String.valueOf(dc.getDouble("precio")))));
        return producto;
    }

    public Empresa documentToEmpleado(Document dc) {
        Empresa empresa = new Empresa();

        empresa.setId(dc.getObjectId("_id"));
        empresa.setNombre(dc.getString("nombre"));
        empresa.setApellidos(dc.getString("apellidos"));
        empresa.setNacimiento(LocalDate.parse(dc.getString("nacimiento")));
        return empresa;
    }

    public Kit_Educativo documentToDepartamento(Document dc) {
        Kit_Educativo kitEducativo = new Kit_Educativo();

        kitEducativo.setId(dc.getObjectId("_id"));
        kitEducativo.setDepartamento(dc.getString("departamento"));
        return kitEducativo;
    }

    public Document objectToDocument(Object obj) {
        Document dc = new Document();

        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;

            dc.append("nombre", producto.getNombre());
            dc.append("grados", producto.getGrados());
            dc.append("precio", producto.getPrecio());
        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;

            dc.append("nombre", empresa.getNombre());
            dc.append("apellidos", empresa.getApellidos());
            dc.append("nacimiento", empresa.getNacimiento().toString());

        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;

            dc.append("departamento", kitEducativo.getDepartamento());
        } else {
            return null;
        }
        return dc;
    }
}
