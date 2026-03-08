package kitEducativo.gui;

import kitEducativo.datos.Empresa;
import kitEducativo.datos.EstadoProducto;
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
        String COLECCION_EMPRESAS = "Empresas";
        empresas = db.getCollection(COLECCION_EMPRESAS);
        String COLECCION_KITS = "Kits";
        kits = db.getCollection(COLECCION_KITS);
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

//    public ArrayList<Producto> getProductos(String comparador) {
//        ArrayList<Producto> lista = new ArrayList<>();
//        Document query = new Document();
//        List<Document> listaCriterios = new ArrayList<>();
//
//        listaCriterios.add(new Document("nombre", new Document("$regex", "/*" + comparador + "/*")));
//        query.append("$or", listaCriterios);
//
//        for (Document document : productos.find(query)) {
//            lista.add(documentToProducto(document));
//        }
//
//        return lista;
//    }

    public ArrayList<Empresa> getEmpresas() {
        ArrayList<Empresa> lista = new ArrayList<>();

        for (Document document : empresas.find()) {
            lista.add(documentToEmpresa(document));
        }
        return lista;
    }

//    public ArrayList<Empresa> getEmpleados(String comparador) {
//        ArrayList<Empresa> lista = new ArrayList<>();
//        Document query = new Document();
//        List<Document> listaCriterios = new ArrayList<>();
//
//        listaCriterios.add(new Document("nombre", new Document("$regex", "/*" + comparador + "/*")));
//        listaCriterios.add(new Document("apellidos", new Document("$regex", "/*" + comparador + "/*")));
//        query.append("$or", listaCriterios);
//
//        for (Document document : empleados.find(query)) {
//            lista.add(documentToEmpresa(document));
//        }
//
//        return lista;
//    }

    public ArrayList<Kit_Educativo> getKits() {
        ArrayList<Kit_Educativo> lista = new ArrayList<>();

        for (Document document : kits.find()) {
            lista.add(documentToKit(document));
        }
        return lista;
    }

//    public ArrayList<Kit_Educativo> getKits(String comparador) {
//        ArrayList<Kit_Educativo> lista = new ArrayList<>();
//        Document query = new Document();
//        List<Document> listaCriterios = new ArrayList<>();
//
//        listaCriterios.add(new Document("departamento", new Document("$regex", "/*" + comparador + "/*")));
//        query.append("$or", listaCriterios);
//
//        for (Document document : departamentos.find(query)) {
//            lista.add(documentToKit(document));
//        }
//
//        return lista;
//    }

    public void guardarObjeto(Object obj) {
        if (obj instanceof Producto) {
            productos.insertOne(objectToDocument(obj));
        } else if (obj instanceof Empresa) {
            empresas.insertOne(objectToDocument(obj));
        } else if (obj instanceof Kit_Educativo) {
            kits.insertOne(objectToDocument(obj));
        }
    }

    public void modificarObjeto(Object obj) {
        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;
            productos.replaceOne(new Document("_id", producto.getId()), objectToDocument(producto));
        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;
            empresas.replaceOne(new Document("_id", empresa.getId()), objectToDocument(empresa));
        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;
            kits.replaceOne(new Document("_id", kitEducativo.getId()), objectToDocument(kitEducativo));
        }
    }

    public void eliminarObjeto(Object obj) {
        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;
            productos.deleteOne(objectToDocument(producto));
        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;
            empresas.deleteOne(objectToDocument(empresa));
        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;
            kits.deleteOne(objectToDocument(kitEducativo));
        }
    }

    public Producto documentToProducto(Document dc) {
        Producto producto = new Producto();
        producto.setId(dc.getObjectId("_id"));
        producto.setNombre(dc.getString("nombre"));
        producto.setDescripcion(dc.getString("descripcion"));
        producto.setModelo(dc.getString("modelo"));
        producto.setMarca(dc.getString("marca"));
        producto.setEstado(EstadoProducto.valueOf(dc.getString("estado")));


        return producto;
    }

    public Empresa documentToEmpresa(Document dc) {
        Empresa empresa = new Empresa();
        empresa.setId(dc.getObjectId("_id"));
        empresa.setNombre(dc.getString("nombre"));
        empresa.setDescripcion(dc.getString("descripcion"));
        empresa.setFechaCreacion(LocalDate.parse(dc.getString("fechaCreacion")));
        empresa.setUbicacion(dc.getString("ubicacion"));
        empresa.setValoracion(dc.getInteger("valoracion"));
        return empresa;
    }

    public Kit_Educativo documentToKit(Document dc) {
        Kit_Educativo kitEducativo = new Kit_Educativo();

        kitEducativo.setId(dc.getObjectId("_id"));
        kitEducativo.setNombre(dc.getString("nombre"));
        kitEducativo.setDescripcion(dc.getString("descripcion"));
        kitEducativo.setCantidad(dc.getInteger("cantidad"));
        kitEducativo.setEmpresasKit(dc.getObjectId("empresasKit"));
        kitEducativo.setProductoKit(dc.getObjectId("productoKit"));
        kitEducativo.setFechaCreacion(LocalDate.parse(dc.getString("fechaCreacion")));
        kitEducativo.setFechaActualizacion(LocalDate.parse(dc.getString("fechaActualizacion")));
        kitEducativo.setPrecio(dc.getDouble("precio"));
        kitEducativo.setValoracion(dc.getInteger("valoracion"));

        return kitEducativo;
    }

    public Document objectToDocument(Object obj) {
        Document dc = new Document();

        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;




            dc.append("nombre", producto.getNombre());
            dc.append("descripcion", producto.getDescripcion());
            dc.append("modelo", producto.getModelo());
            dc.append("marca", producto.getMarca());
            dc.append("estado", producto.getEstado().name());

        } else if (obj instanceof Empresa) {
            Empresa empresa = (Empresa) obj;



            dc.append("nombre", empresa.getNombre());
            dc.append("descripcion", empresa.getDescripcion());
            dc.append("fechaCreacion", empresa.getFechaCreacion().toString());
            dc.append("ubicacion", empresa.getUbicacion());
            dc.append("valoracion", empresa.getValoracion());



        } else if (obj instanceof Kit_Educativo) {
            Kit_Educativo kitEducativo = (Kit_Educativo) obj;


            dc.append("nombre", kitEducativo.getNombre());
            dc.append("descripcion", kitEducativo.getDescripcion());
            dc.append("cantidad", kitEducativo.getCantidad());
            dc.append("empresasKit", kitEducativo.getEmpresasKit());
            dc.append("productoKit", kitEducativo.getProductoKit());
            dc.append("fechaCreacion", kitEducativo.getFechaCreacion().toString());
            dc.append("fechaActualizacion", kitEducativo.getFechaActualizacion().toString());
            dc.append("precio", kitEducativo.getPrecio());
            dc.append("valoracion", kitEducativo.getValoracion());





        } else {
            return null;
        }
        return dc;
    }
}
