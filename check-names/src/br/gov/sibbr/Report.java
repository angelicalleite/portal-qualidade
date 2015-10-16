package br.gov.sibbr;


public class Report {

    BD bd = new BD();

    public void start() {
        /*
		bd.abreConexao();
		
		ArrayList<String> sourcefileid = new ArrayList<String>();
		ArrayList<String> resource_name = new ArrayList<String>();
		
		ResultSet rs = bd.consulta("Select sourcefileid, resource_name from public.resource_contact");
		
		try{
			while(rs.next()){
				sourcefileid.add(rs.getString("sourcefileid"));
				resource_name.add(rs.getString("resource_name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String lista[] = new String[resource_name.size()];
		for(int i = 0; i < resource_name.size(); i++){
			lista[i] = resource_name.get(i);
			System.out.println("Resource Name: " + lista[i]);
		}
		*/
    }

}
