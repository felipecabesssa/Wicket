package br.com.timtec.agendaeletronica;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import br.com.timtec.agendaeletronica.contato.Contato;

public class Pesquisar extends BasePage{
	private static final long serialVersionUID = -6477084019452203275L;
	
	public Pesquisar(){
		Form<String> formularioPesquisa = new Form<String>("formularioPesquisa");
		add(formularioPesquisa);
		
		TextField<String> pesquisaNome = new TextField<String>("pesquisaNome", new Model<String>());
		formularioPesquisa.add(pesquisaNome);
		
		final WebMarkupContainer containerResultados = new WebMarkupContainer("divResultados");
		containerResultados.setVisible(false);
		containerResultados.setOutputMarkupPlaceholderTag(true);
		add(containerResultados);
		
		PropertyListView<Contato> listaResultados = new PropertyListView<Contato>("contatos", new ListModel<Contato>()) {
			private static final long serialVersionUID = 5981445618825912510L;

			@Override
			protected void populateItem(final ListItem<Contato> listItem) {
				listItem.add(new Label("nome"));
				listItem.add(new Label("email"));
				listItem.add(new Label("telefone"));
				listItem.add(new Label("estadoCivil"));
				listItem.add(new Link<Void>("linkEditar"){
					private static final long serialVersionUID = -9102939954036201747L;
					
					@Override
					public void onClick(){
						setResponsePage(new Editar(listItem.getModelObject()));
					}
				});
			}
		};
		containerResultados.add(listaResultados);
		
		AjaxButton botaoPesquisar = new AjaxButton("botaoPesquisar", formularioPesquisa){
			private static final long serialVersionUID = -5481841024415399184L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form){
				containerResultados.setVisible(true);
				target.add(containerResultados);
			}
			
		};
		formularioPesquisa.add(botaoPesquisar);
	}

}
